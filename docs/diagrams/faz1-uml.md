# UML Sınıf Diyagramı — Faz 1 (Creational)

Factory Method + Singleton uygulandıktan sonra:

```mermaid
classDiagram
    class Bildirim {
        <<interface>>
        +gonder(alici, baslik, mesaj, oncelik) boolean
        +formatla(baslik, mesaj) String
        +aliciDogrula(alici) boolean
        +getTipAdi() String
    }

    class EmailBildirim {
        -smtpSunucu: String
        -smtpPort: int
        -gonderenAdres: String
        +gonder() boolean
        +formatla() String
        +aliciDogrula() boolean
    }

    class SmsBildirim {
        -apiAnahtari: String
        -gonderenNumara: String
        -apiUrl: String
        +gonder() boolean
        +formatla() String
        +aliciDogrula() boolean
    }

    class PushBildirim {
        -apiAnahtari: String
        -sunucuUrl: String
        -uygulamaId: String
        +gonder() boolean
        +formatla() String
        +aliciDogrula() boolean
    }

    class BildirimTipi {
        <<enumeration>>
        EMAIL
        SMS
        PUSH
    }

    class BildirimFactory {
        +bildirimOlustur(tip)$ Bildirim
    }

    class BildirimServisi {
        -instance$: BildirimServisi
        -bildirimGecmisi: List
        -BildirimServisi()
        +getInstance()$ BildirimServisi
        +bildirimGonder() boolean
        +topluBildirimGonder() void
    }

    Bildirim <|.. EmailBildirim
    Bildirim <|.. SmsBildirim
    Bildirim <|.. PushBildirim
    BildirimFactory ..> Bildirim : creates
    BildirimFactory ..> BildirimTipi : uses
    BildirimServisi --> BildirimFactory : uses
```

**Kazanımlar:**
- Her bildirim tipi kendi sınıfında (SRP)
- Factory Method ile merkezi nesne yaratma
- Singleton ile tek instance garantisi
- If-else zincirleri polimorfizm ile kaldırıldı
