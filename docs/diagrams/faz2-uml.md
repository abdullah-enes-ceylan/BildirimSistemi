# UML Sınıf Diyagramı — Faz 2 (Structural)

Decorator + Facade uygulandıktan sonra:

```mermaid
classDiagram
    class Bildirim {
        <<interface>>
        +gonder() boolean
        +formatla() String
        +aliciDogrula() boolean
        +getTipAdi() String
    }

    class EmailBildirim
    class SmsBildirim
    class PushBildirim

    class BildirimDecorator {
        <<abstract>>
        #sarmalanmisBildirim: Bildirim
        +BildirimDecorator(bildirim)
    }

    class LoglamaDecorator {
        +gonder() boolean
    }

    class TekrarDenemeDecorator {
        -maxDeneme: int
        -beklemeMs: long
        +gonder() boolean
    }

    class SifrelemeDecorator {
        +gonder() boolean
        -sifrele(metin) String
    }

    class BildirimFacade {
        -servis: BildirimServisi
        -loglamaAktif: boolean
        -sifrelemeAktif: boolean
        +emailGonder() boolean
        +smsGonder() boolean
        +pushGonder() boolean
        +acilBildirimGonder() boolean
    }

    class BildirimFactory {
        +bildirimOlustur()$ Bildirim
    }

    class BildirimServisi {
        +getInstance()$ BildirimServisi
        +bildirimGonder() boolean
    }

    Bildirim <|.. EmailBildirim
    Bildirim <|.. SmsBildirim
    Bildirim <|.. PushBildirim
    Bildirim <|.. BildirimDecorator
    BildirimDecorator <|-- LoglamaDecorator
    BildirimDecorator <|-- TekrarDenemeDecorator
    BildirimDecorator <|-- SifrelemeDecorator
    BildirimFacade --> BildirimServisi : delegates
    BildirimFacade --> BildirimFactory : uses
    BildirimFacade --> BildirimDecorator : creates
```

**Kazanımlar:**
- Decorator ile davranışlar katmanlanabilir (Log + Şifreleme + Retry)
- Facade ile karmaşık alt sistem tek satırla kullanılabilir
- Mevcut sınıflar hiç değişmedi (OCP)
