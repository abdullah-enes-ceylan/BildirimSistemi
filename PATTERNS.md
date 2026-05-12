# PATTERNS.md — Uygulanan Tasarım Örüntüleri

Bu dosya, her fazda uygulanan tasarım örüntülerini belgeler.

---

## Faz 0 — Başlangıç Durumu

Tasarım örüntüsü yok. Tüm bildirim tipleri tek bir God Class'ta if-else zincirleriyle yönetiliyordu.

---

## Faz 1 — Creational Örüntüler

### 1. Factory Method Pattern
**Dosya:** `BildirimFactory.java`, `BildirimTipi.java`

Bildirim nesnelerinin yaratılmasını merkezi bir factory'ye taşıdık. İstemci kodu `BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL)` çağırarak somut sınıfı bilmeden nesne alır. Yeni tip eklemek için mevcut bildirim sınıflarına dokunmak gerekmez.

### 2. Singleton Pattern
**Dosya:** `BildirimServisi.java`

Private constructor + double-checked locking ile thread-safe Singleton. Tüm uygulama tek bir `BildirimServisi` instance'ı paylaşır, bildirim geçmişi tutarlı kalır.

---

## Faz 2 — Structural Örüntüler

### 3. Decorator Pattern
**Dosyalar:** `BildirimDecorator.java`, `LoglamaDecorator.java`, `TekrarDenemeDecorator.java`, `SifrelemeDecorator.java`

Bildirimlere loglama, retry, şifreleme gibi davranışları mevcut sınıfları değiştirmeden ekler. Decorator'lar zincirlenebilir:
```java
Bildirim b = new LoglamaDecorator(new SifrelemeDecorator(email));
```

### 4. Facade Pattern
**Dosya:** `BildirimFacade.java`

Factory, Decorator, Singleton karmaşıklığını gizleyerek tek satırlık API sunar:
```java
facade.emailGonder("alici@test.com", "Başlık", "Mesaj");
```

---

## Faz 3 — Behavioral Örüntüler

### 5. Observer Pattern
**Dosyalar:** `BildirimOlayDinleyici.java`, `BildirimOlayYoneticisi.java`, `IstatistikDinleyici.java`, `GecmisDinleyici.java`

**Neden uygulandı:**
İstatistik toplama ve geçmiş kaydı `BildirimServisi`'ne sıkı bağlıydı. Yeni bir izleme özelliği (örn. alarm, dosyaya loglama) eklemek servisi değiştirmeyi gerektiriyordu.

**Ne kazandık:**
- `BildirimOlayDinleyici` arayüzünü implement eden herhangi bir sınıf, bildirim olaylarını dinleyebilir
- `IstatistikDinleyici`: tip bazlı başarılı/başarısız sayıları otomatik toplar
- `GecmisDinleyici`: zaman damgalı geçmiş kaydı tutar
- **OCP kanıtı:** Yeni bir dinleyici (örn. `AlarmDinleyici`) eklemek için sadece `BildirimOlayDinleyici` implement edilir — mevcut hiçbir sınıf değişmez

**Sınıf İlişkisi:**
```
BildirimOlayDinleyici (interface)  ←  Observer
├── IstatistikDinleyici
└── GecmisDinleyici

BildirimOlayYoneticisi  ←  Subject
└── dinleyiciler: List<BildirimOlayDinleyici>
```

### 6. Strategy Pattern
**Dosyalar:** `FiltrelemeStratejisi.java`, `HepsiniGonderStratejisi.java`, `OncelikFiltrelemeStratejisi.java`, `SessizModStratejisi.java`

**Neden uygulandı:**
Bildirimlerin filtrelenmesi (hangi bildirimin gönderilip hangisinin engelleneceği) sabit bir mantık olmamalıydı. Farklı senaryolarda (gece modu, sessiz mod, acil durum) farklı filtreleme kuralları gerekiyordu.

**Ne kazandık:**
- `FiltrelemeStratejisi` arayüzü ile filtreleme algoritması soyutlandı
- `setFiltrelemeStratejisi()` ile runtime'da strateji değiştirilebilir
- `HepsiniGonderStratejisi`: varsayılan, filtresiz gönderim
- `OncelikFiltrelemeStratejisi`: minimum öncelik altını engeller
- `SessizModStratejisi`: belirli tipleri engeller, acil bildirimler hariç
- **OCP kanıtı:** Yeni filtreleme kuralı = yeni sınıf, mevcut kod değişmez

**Strateji Değişimi Örneği:**
```java
// Normal mod
servis.setFiltrelemeStratejisi(new HepsiniGonderStratejisi());

// Gece modu: sadece acil bildirimler
servis.setFiltrelemeStratejisi(new OncelikFiltrelemeStratejisi(4));

// Sessiz mod: Push/SMS engelli
SessizModStratejisi sessiz = new SessizModStratejisi();
sessiz.tipEngelle("PUSH");
servis.setFiltrelemeStratejisi(sessiz);
```

---

## Özet Tablo

| # | Örüntü | Kategori | Dosyalar |
|---|--------|----------|----------|
| 1 | Factory Method | Creational | `BildirimFactory`, `BildirimTipi` |
| 2 | Singleton | Creational | `BildirimServisi` |
| 3 | Decorator | Structural | `BildirimDecorator`, `LoglamaDecorator`, `TekrarDenemeDecorator`, `SifrelemeDecorator` |
| 4 | Facade | Structural | `BildirimFacade` |
| 5 | Observer | Behavioral | `BildirimOlayDinleyici`, `BildirimOlayYoneticisi`, `IstatistikDinleyici`, `GecmisDinleyici` |
| 6 | Strategy | Behavioral | `FiltrelemeStratejisi`, `HepsiniGonderStratejisi`, `OncelikFiltrelemeStratejisi`, `SessizModStratejisi` |
