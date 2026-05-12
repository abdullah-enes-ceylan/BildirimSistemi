# PATTERNS.md — Uygulanan Tasarım Örüntüleri

Bu dosya, her fazda uygulanan tasarım örüntülerini belgeler.

---

## Faz 0 — Başlangıç Durumu

Henüz tasarım örüntüsü uygulanmadı. Tüm bildirim tipleri `BildirimServisi` adlı tek bir God Class içinde if-else zincirleriyle yönetiliyordu.

**Sorunlar:** God Class, If-Else zincirleri, Kod tekrarı, Hatalı Singleton, Sıkı bağlılık.

---

## Faz 1 — Creational Örüntüler

### 1. Factory Method Pattern

**Nerede uygulandı:** `BildirimFactory.bildirimOlustur(BildirimTipi tip)`

**Neden uygulandı:**
Başlangıç kodunda bildirim nesneleri doğrudan `BildirimServisi` içinde if-else zincirleriyle yaratılıyordu. Yeni bildirim tipi eklemek birçok metotta değişiklik gerektiriyordu.

**Ne kazandık:**
- Nesne yaratma sorumluluğu merkezi bir factory'ye taşındı
- Polimorfizm sayesinde if-else zincirleri ortadan kalktı
- Yeni tip eklemek için mevcut sınıflara dokunmak gerekmiyor

### 2. Singleton Pattern

**Nerede uygulandı:** `BildirimServisi.getInstance()`

**Neden uygulandı:**
Constructor public'ti ve thread-safe değildi. Birden fazla instance bildirim geçmişi tutarsızlığına yol açabilirdi.

**Ne kazandık:**
- Private constructor + double-checked locking ile thread-safe Singleton
- Bildirim geçmişi tek noktada tutarlı

---

## Faz 2 — Structural Örüntüler

### 3. Decorator Pattern

**Nerede uygulandı:** `BildirimDecorator` (abstract), `LoglamaDecorator`, `TekrarDenemeDecorator`, `SifrelemeDecorator`

**Neden uygulandı:**
Bildirimlere loglama, tekrar deneme, şifreleme gibi ek davranışlar eklemek gerekiyordu. Bu davranışları doğrudan bildirim sınıflarına eklemek:
- Her bildirim tipinde (Email, SMS, Push) aynı kodu tekrarlamayı gerektirirdi
- Hangi davranışın aktif olacağını runtime'da seçmeyi zorlaştırırdı
- Yeni davranış eklemek mevcut sınıfları değiştirmeyi gerektirirdi (OCP ihlali)

**Ne kazandık:**
- Davranışlar bağımsız decorator sınıflarında — her biri tek sorumluluk
- Decorator'lar **zincirlenebilir**: `new LoglamaDecorator(new SifrelemeDecorator(email))`
- Runtime'da hangi davranışların aktif olacağı seçilebilir
- Yeni davranış eklemek için sadece yeni decorator yazılır — OCP korunuyor
- Mevcut bildirim sınıfları hiç değişmedi!

**Decorator Zinciri Örneği:**
```java
// Sade bildirim
Bildirim email = BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL);

// Loglama ekle
Bildirim loglu = new LoglamaDecorator(email);

// Şifreleme + loglama ekle
Bildirim tamDonatimli = new LoglamaDecorator(new SifrelemeDecorator(email));
```

**Sınıf Yapısı:**
```
Bildirim (interface)
  ├── EmailBildirim
  ├── SmsBildirim
  ├── PushBildirim
  └── BildirimDecorator (abstract)
        ├── LoglamaDecorator
        ├── TekrarDenemeDecorator
        └── SifrelemeDecorator
```

### 4. Facade Pattern

**Nerede uygulandı:** `BildirimFacade`

**Neden uygulandı:**
Bildirim göndermek için istemci kodunun şunları bilmesi gerekiyordu:
1. `BildirimFactory` ile nesne oluşturmak
2. Hangi `BildirimTipi` enum değerini kullanmak
3. Gerekli decorator'ları elle zincirlenmek
4. `BildirimServisi.getInstance()` ile singleton almak

Bu karmaşıklık, özellikle basit bildirim göndermek isteyen istemci kodu için gereksizdi.

**Ne kazandık:**
- **Tek satırla bildirim**: `facade.emailGonder("alici@test.com", "Başlık", "Mesaj")`
- Factory, Decorator, Singleton karmaşıklığı gizleniyor
- İstemci kodu alt sistemi bilmek zorunda değil
- Acil bildirim gibi özel senaryolar hazır metotlarla sunuluyor
- Loglama/şifreleme yapılandırması constructor'da bir kez yapılıyor

**Facade Kullanımı:**
```java
// Önce (Facade olmadan)
BildirimServisi servis = BildirimServisi.getInstance();
Bildirim bildirim = BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL);
Bildirim loglu = new LoglamaDecorator(bildirim);
loglu.gonder("alici@test.com", "Başlık", "Mesaj", 1);

// Sonra (Facade ile)
BildirimFacade facade = new BildirimFacade();
facade.emailGonder("alici@test.com", "Başlık", "Mesaj");
```

---

## Faz 3 — Behavioral Örüntüler
_Henüz uygulanmadı._
