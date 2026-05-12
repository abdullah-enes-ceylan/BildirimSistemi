# AI Log — Faz 1: Creational Örüntüler

## Kullanılan AI Aracı
**Claude (Antigravity)** — pair programming modunda

---

## 1. AI'a Ne Sordum?

### Prompt 1 — Problem Analizi (Faz 0'dan)
> "Bu kodda hangi tasarım sorunlarını görüyorsun? Hangi tasarım örüntüleri bu sorunları çözebilir?"

### Prompt 2 — Faz 1 Planlama
> "Bildirim Sistemi'ndeki God Class ve if-else zincirlerini çözmek için hangi Creational örüntüleri uygulayabiliriz? Factory Method ve Singleton nasıl uygulanmalı?"

---

## 2. AI Ne Yanıtladı? (Özet)

### Factory Method İçin:
- `Bildirim` adında bir arayüz oluşturulmasını önerdi
- Her bildirim tipi (Email, SMS, Push) için ayrı sınıflar tanımlanmasını önerdi
- `BildirimFactory` ile merkezi nesne yaratma mekanizması kurulmasını önerdi
- `BildirimTipi` enum'u ile type-safe tip tanımlaması önerdi

### Singleton İçin:
- Private constructor + double-checked locking önerdi
- `volatile` anahtar kelimesiyle thread-safety sağlanmasını önerdi
- Alternatif olarak enum-based Singleton da önerdi ama bu projede uygun olmadığını belirtti (çünkü state tutuyoruz)

---

## 3. Ben Ne Uyguladım ve Neden?

### Aynı Uyguladığım Kısımlar:
- `Bildirim` arayüzü — AI'ın önerdiği gibi `gonder()`, `formatla()`, `aliciDogrula()`, `getTipAdi()` metodlarını içeriyor
- Factory Method — `BildirimFactory.bildirimOlustur(BildirimTipi)` şeklinde uyguladım
- Singleton — Double-checked locking ile

### Farklı Yaptığım Kısımlar:
- AI abstract class önerdi, ben interface tercih ettim. Çünkü şu an ortak state yok; sadece davranış sözleşmesi tanımlamak yeterli. İleride ortak davranış gerekirse abstract class'a geçilebilir.
- AI her bildirim tipinin ayarlarını constructor parametresi olarak almasını önerdi, ben de aynısını yaptım — bu sayede ayarlar sınıf dışından enjekte edilebilir (ileride DI için uygun).

### Reddettiğim Öneriler:
- AI, `BildirimFactory`'yi abstract factory olarak tasarlamamı önerdi (her kanal için ayrı factory). Bu aşamada over-engineering olur, basit Factory Method yeterli. İleride gerekirse Abstract Factory'ye geçilebilir.

---

## 4. Değerlendirme

AI'ın önerileri genel olarak tutarlıydı ve doğru yöndeydi. Ancak bazı noktalarda gereksiz karmaşıklık eklemeye meyilliydi (Abstract Factory gibi). YAGNI (You Ain't Gonna Need It) prensibi gereği basit tutmayı tercih ettim. AI'ın en faydalı katkısı, sınıf yapısının nasıl ayrıştırılacağına dair net bir yol haritası sunmasıydı.
