# AI Log — Faz 2: Structural Örüntüler

## Kullanılan AI Aracı
**Claude (Antigravity)** — pair programming modunda

---

## 1. AI'a Ne Sordum?

### Prompt 1 — Örüntü Seçimi
> "Bildirim Sistemime yeni özellikler (loglama, retry, şifreleme) eklemek istiyorum. Adapter pattern burada uygun mu, yoksa Decorator mı? Farkını açıkla."

### Prompt 2 — Facade Gerekliliği
> "İstemci kodu Factory, Decorator ve Singleton ile doğrudan çalışıyor. Bu karmaşıklığı gizlemek için Facade mı yoksa başka bir örüntü mü kullanmalıyım?"

---

## 2. AI Ne Yanıtladı? (Özet)

### Adapter vs Decorator Sorusuna:
- **Adapter**: Uyumsuz arayüzleri uyumlu hale getirmek için kullanılır. Örneğin farklı SMS API'leri aynı arayüze dönüştürmek.
- **Decorator**: Mevcut nesneye ek davranışlar eklemek için kullanılır. Loglama, retry, şifreleme gibi cross-cutting concerns için idealdir.
- **Karar**: Bu durumda Decorator daha uygun çünkü mevcut bildirim nesnelerine davranış ekliyoruz, arayüz dönüştürmüyoruz.

### Facade Sorusuna:
- AI, Facade Pattern'i doğru buldu ve şunları önerdi:
  - Tip bazlı kısayol metodları (emailGonder, smsGonder)
  - Yapılandırma parametreleri (loglama on/off, şifreleme on/off)
  - Acil bildirim gibi iş senaryolarını kapsayan yüksek seviye metodlar

---

## 3. Ben Ne Uyguladım ve Neden?

### Aynı Uyguladığım Kısımlar:
- Decorator Pattern — abstract base + 3 concrete decorator (Loglama, TekrarDeneme, Sifreleme)
- Facade Pattern — basit API ile karmaşıklığı gizleme

### Farklı Yaptığım Kısımlar:
- AI, Adapter Pattern'i de eklememizi önerdi (farklı SMS API'leri için). Ben bunu reddettim çünkü şu an tek bir SMS API kullanıyoruz — YAGNI prensibi.
- AI, decorator'ları otomatik zincirleme için bir Builder önermişti. Ben bunu Facade içinde `hazirla()` private metodu ile daha basit çözdüm.

### AI'ın Yanlış/Eksik Önerdiği Şeyler:
- AI başta Adapter + Bridge + Decorator üçlüsünü önerdi. Ancak bir bildirim sistemi için Bridge Pattern (soyutlama-implementasyon ayrımı) gereksiz karmaşıklık eklerdi. Mevcut `Bildirim` arayüzü zaten yeterli soyutlamayı sağlıyor.
- AI, TekrarDenemeDecorator'da exponential backoff (üstel bekleme) uygulamasını önerdi. Ben basit sabit bekleme süresini tercih ettim çünkü demo amaçlı yeterli. Gerçek uygulamada exponential backoff daha uygun olurdu.

---

## 4. Değerlendirme

AI, Adapter vs Decorator farkını açıklarken çok başarılıydı ve doğru örüntüyü seçmeme yardımcı oldu. Ancak bazen gereğinden fazla örüntü uygulamaya çalışıyordu (Bridge gibi). "Her probleme bir örüntü" yaklaşımı yerine, gerçekten ihtiyaç olan yerde örüntü uygulamak daha doğru.
