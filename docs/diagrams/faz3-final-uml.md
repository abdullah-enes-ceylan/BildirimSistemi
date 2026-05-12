# UML Sınıf Diyagramı — Faz 3 (Behavioral) — Final

Tüm 6 örüntü uygulandıktan sonra:

```mermaid
classDiagram
    class Bildirim {
        <<interface>>
        +gonder(alici, baslik, mesaj, oncelik) boolean
        +formatla(baslik, mesaj) String
        +aliciDogrula(alici) boolean
        +getTipAdi() String
    }

    class EmailBildirim
    class SmsBildirim
    class PushBildirim

    class BildirimDecorator {
        <<abstract>>
        #sarmalanmisBildirim: Bildirim
    }
    class LoglamaDecorator
    class TekrarDenemeDecorator
    class SifrelemeDecorator

    class BildirimFactory {
        +bildirimOlustur(tip)$ Bildirim
    }

    class BildirimServisi {
        -instance$: BildirimServisi
        -filtrelemeStratejisi: FiltrelemeStratejisi
        +getInstance()$ BildirimServisi
        +bildirimGonder() boolean
        +setFiltrelemeStratejisi() void
    }

    class BildirimFacade {
        +emailGonder() boolean
        +smsGonder() boolean
        +pushGonder() boolean
    }

    class BildirimOlayDinleyici {
        <<interface>>
        +bildirimGonderildi(tip, alici, baslik)
        +bildirimBasarisiz(tip, alici, baslik, sebep)
    }

    class BildirimOlayYoneticisi {
        -dinleyiciler: List
        +dinleyiciEkle(dinleyici)
        +basariliGonderimBildir()
        +basarisizGonderimBildir()
    }

    class IstatistikDinleyici {
        -basariliSayilari: Map
        -basarisizSayilari: Map
        +istatistikleriYazdir()
    }

    class GecmisDinleyici {
        -gecmis: List
        +gecmisYazdir()
    }

    class FiltrelemeStratejisi {
        <<interface>>
        +gonderilsinMi(tip, alici, oncelik) boolean
        +getAd() String
    }

    class HepsiniGonderStratejisi
    class OncelikFiltrelemeStratejisi {
        -minimumOncelik: int
    }
    class SessizModStratejisi {
        -engellenenTipler: Set
        +tipEngelle(tip)
        +tipIzinVer(tip)
    }

    Bildirim <|.. EmailBildirim
    Bildirim <|.. SmsBildirim
    Bildirim <|.. PushBildirim
    Bildirim <|.. BildirimDecorator
    BildirimDecorator <|-- LoglamaDecorator
    BildirimDecorator <|-- TekrarDenemeDecorator
    BildirimDecorator <|-- SifrelemeDecorator

    BildirimFactory ..> Bildirim : creates
    BildirimServisi --> BildirimFactory : uses
    BildirimServisi --> BildirimOlayYoneticisi : notifies
    BildirimServisi --> FiltrelemeStratejisi : filters with

    BildirimFacade --> BildirimServisi : delegates

    BildirimOlayDinleyici <|.. IstatistikDinleyici
    BildirimOlayDinleyici <|.. GecmisDinleyici
    BildirimOlayYoneticisi o-- BildirimOlayDinleyici : observers

    FiltrelemeStratejisi <|.. HepsiniGonderStratejisi
    FiltrelemeStratejisi <|.. OncelikFiltrelemeStratejisi
    FiltrelemeStratejisi <|.. SessizModStratejisi
```

**Final Mimari — 6 Örüntü:**
1. **Factory Method** → BildirimFactory creates Bildirim
2. **Singleton** → BildirimServisi.getInstance()
3. **Decorator** → BildirimDecorator chain
4. **Facade** → BildirimFacade simplifies API
5. **Observer** → BildirimOlayYoneticisi notifies listeners
6. **Strategy** → FiltrelemeStratejisi swappable at runtime
