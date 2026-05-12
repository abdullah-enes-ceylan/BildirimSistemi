# UML Sınıf Diyagramı — Faz 0 (Başlangıç)

Tüm sorumluluklar tek bir God Class'ta:

```mermaid
classDiagram
    class BildirimServisi {
        -smtpSunucu: String
        -smtpPort: int
        -smtpKullaniciAdi: String
        -smtpSifre: String
        -smsApiAnahtari: String
        -smsGonderenNumara: String
        -smsApiUrl: String
        -pushApiAnahtari: String
        -pushSunucuUrl: String
        -pushUygulamaId: String
        -bildirimGecmisi: List
        -instance$: BildirimServisi
        +bildirimGonder(tip, alici, baslik, mesaj, oncelik) boolean
        +topluBildirimGonder(tip, alicilar, baslik, mesaj, oncelik) void
        +bildirimFormatla(tip, baslik, mesaj) String
        +aliciDogrula(tip, alici) boolean
        +gecmisGetir(filtreTip) List
        +istatistikYazdir() void
        +smtpAyarlariGuncelle() void
        +smsAyarlariGuncelle() void
        +pushAyarlariGuncelle() void
    }

    class Main {
        +main(args)$ void
    }

    Main --> BildirimServisi : kullanir
```

**Sorunlar:**
- Tek sınıfta 3 farklı bildirim tipi, 10+ alan, 10+ metot
- Tüm bildirim mantığı if-else zincirleriyle ayrılıyor
- SRP, OCP, DRY ihlalleri
