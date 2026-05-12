# PROBLEMS.md — Başlangıç Kodunun Analizi (Faz 0)

## Benim Tespit Ettiğim Sorunlar

### 1. God Class (Tanrı Sınıfı) Problemi
`BildirimServisi` sınıfı, e-posta, SMS ve push bildirimlerinin tüm sorumluluklarını tek başına üstleniyor. SMTP ayarları, SMS API ayarları, push bildirim ayarları ve bildirim geçmişi hepsi aynı sınıfta. Bu, Single Responsibility Principle'ı (SRP) açıkça ihlal ediyor.

### 2. If-Else Zinciri ile Tip Kontrolü
`bildirimGonder()`, `bildirimFormatla()`, `aliciDogrula()` ve `topluBildirimGonder()` metotlarının hepsinde aynı `if-else` yapısı tekrarlanıyor. Yeni bir bildirim tipi (örn. Telegram, WhatsApp) eklemek istersek, her metotta yeni bir `else if` bloğu eklemek zorunda kalıyoruz. Bu Open/Closed Principle'ı (OCP) ihlal ediyor.

### 3. Kod Tekrarı (Duplicate Code)
Bildirim formatlama mantığı hem `bildirimGonder()` hem de `bildirimFormatla()` metotlarında tekrarlanıyor. SMS karakter limiti kontrolü iki farklı yerde yapılıyor. Bu durum bakımı zorlaştırıyor ve bug riski yaratıyor.

### 4. Yanlış Singleton Uygulaması
`getInstance()` metodu var ama constructor `public` — yani hem `new BildirimServisi()` hem de `getInstance()` ile nesne oluşturulabiliyor. Thread-safe de değil. Singleton deseni ya düzgün uygulanmalı ya da hiç kullanılmamalı.

### 5. Sıkı Bağlılık (Tight Coupling)
Bildirim gönderme, formatlama, doğrulama, geçmiş kaydı ve istatistik hesaplama hepsi aynı sınıfa bağlı. Bu bileşenlerden birini değiştirmek (örn. farklı bir loglama sistemi) tüm sınıfı etkileyebilir.

### 6. Sabit Kodlanmış Yapılandırma
SMTP ayarları, API anahtarları ve URL'ler doğrudan sınıf içinde sabit kodlanmış. Bu bilgiler bir yapılandırma dosyasından veya çevre değişkenlerinden okunmalı.

### 7. Rate Limiting Mantığı İş Mantığıyla Karışık
`topluBildirimGonder()` metodu içindeki rate limiting (bekleme süreleri) bildirim gönderme mantığıyla iç içe geçmiş. Bu ayrı bir concern olmalı.

---

## AI'ın Tespit Ettiği Sorunlar

> **Kullanılan AI:** Claude (Antigravity)
> **Prompt:** "Bu kodda hangi tasarım sorunlarını görüyorsun? Hangi tasarım örüntüleri bu sorunları çözebilir? Her sorun için kısa bir açıklama yaz."

### AI'ın Yanıtı:

1. **God Class / SRP İhlali:** `BildirimServisi` sınıfı çok fazla sorumluluk üstleniyor — bildirim gönderme, formatlama, doğrulama, geçmiş yönetimi, ayar yönetimi. → **Factory Method + Strategy Pattern** ile çözülebilir.

2. **If-Else Zinciri / OCP İhlali:** Her metotta bildirim tipine göre tekrarlanan if-else blokları var. Yeni tip eklemek mevcut kodu değiştirmeyi gerektiriyor. → **Factory Method** ile bildirim nesneleri oluşturulabilir, **Strategy Pattern** ile davranışlar soyutlanabilir.

3. **Kod Tekrarı (DRY İhlali):** Formatlama mantığı birden fazla yerde tekrarlanıyor. → **Template Method** ile ortak adımlar soyutlanabilir.

4. **Hatalı Singleton:** Constructor public olmasına rağmen Singleton benzeri static getInstance var. Thread-safe değil. → **Singleton Pattern** düzgün uygulanmalı (enum veya double-checked locking).

5. **Tight Coupling:** Tüm bildirim kanalları, formatlama ve doğrulama aynı sınıfa bağlı. Test yazmak ve mock'lamak zor. → **Adapter/Bridge Pattern** ile kanal implementasyonları ayrıştırılabilir.

6. **Genişletilemezlik:** Yeni özellikler (loglama, retry mekanizması, öncelik sıralaması) eklemek mevcut kodu değiştirmeyi gerektiriyor. → **Decorator Pattern** ile davranışlar katmanlanabilir, **Observer Pattern** ile olay tabanlı bildirim yapılabilir.

7. **Yapılandırma Yönetimi Eksikliği:** Ayarlar sınıf içinde sabit kodlanmış. → **Builder Pattern** ile karmaşık konfigürasyon yönetimi yapılabilir.

---

## Karşılaştırma: Ben vs AI

| Sorun | Ben Buldum | AI Buldu | Notlar |
|-------|:----------:|:--------:|--------|
| God Class / SRP | ✅ | ✅ | İkimiz de aynı tespiti yaptık |
| If-Else zinciri / OCP | ✅ | ✅ | İkimiz de aynı tespiti yaptık |
| Kod tekrarı / DRY | ✅ | ✅ | İkimiz de aynı tespiti yaptık |
| Yanlış Singleton | ✅ | ✅ | İkimiz de aynı tespiti yaptık |
| Tight Coupling | ✅ | ✅ | İkimiz de aynı tespiti yaptık |
| Sabit kodlanmış yapılandırma | ✅ | ✅ | AI bunu Builder Pattern ile çözmeyi önerdi |
| Rate limiting karışıklığı | ✅ | ❌ | AI bu detayı atladı |
| Genişletilemezlik (Decorator/Observer) | ❌ | ✅ | AI daha ileriye baktı — Decorator ve Observer önerdi |

### Sonuç
AI ile benzer sorunları tespit ettik. Ben daha çok mevcut kodun somut problemlerine odaklanırken (rate limiting karışıklığı gibi), AI daha çok mimari seviyede düşünerek ilerideki fazlarda uygulanabilecek çözüm örüntüleri önerdi (Decorator, Observer gibi). AI'ın genişletilemezlik konusundaki öngörüsü faydalıydı, benim atladığım bir perspektifti.
