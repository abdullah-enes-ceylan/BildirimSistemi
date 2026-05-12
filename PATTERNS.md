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
Başlangıç kodunda bildirim nesneleri doğrudan `BildirimServisi` içinde if-else zincirleriyle yaratılıyor ve yönetiliyordu. Yeni bir bildirim tipi (örn. Telegram) eklemek için `BildirimServisi` sınıfının birçok metodunda değişiklik yapmak gerekiyordu.

**Ne kazandık:**
- Nesne yaratma sorumluluğu merkezi bir factory'ye taşındı
- `Bildirim` arayüzü sayesinde polimorfizm kullanılıyor — if-else zincirleri ortadan kalktı
- Yeni bildirim tipi eklemek için sadece:
  1. Yeni sınıf oluştur (örn. `TelegramBildirim implements Bildirim`)
  2. `BildirimTipi` enum'una değer ekle
  3. `BildirimFactory`'ye bir case ekle
- Mevcut bildirim sınıflarına dokunmaya gerek yok

**Sınıf Yapısı:**
```
Bildirim (interface)
├── EmailBildirim
├── SmsBildirim
└── PushBildirim

BildirimFactory.bildirimOlustur(BildirimTipi) → Bildirim
```

### 2. Singleton Pattern

**Nerede uygulandı:** `BildirimServisi.getInstance()`

**Neden uygulandı:**
Başlangıç kodunda `BildirimServisi`'nin constructor'ı `public` idi ve `getInstance()` metodu thread-safe değildi. Birden fazla instance oluşturulabiliyordu, bu da bildirim geçmişinin tutarsız olmasına yol açabilirdi.

**Ne kazandık:**
- `private` constructor ile dışarıdan `new` ile instance oluşturma engellendi
- Double-checked locking ile thread-safe Singleton garantisi
- Tüm uygulama tek bir `BildirimServisi` instance'ı paylaşıyor
- Bildirim geçmişi tutarlı bir şekilde tek noktada tutuluyor

**Önce → Sonra:**
```java
// ÖNCE (Faz 0) - Hatalı
public BildirimServisi() { }  // public constructor
public static BildirimServisi getInstance() {
    if (instance == null) { instance = new BildirimServisi(); }  // thread-safe değil
    return instance;
}

// SONRA (Faz 1) - Doğru Singleton
private BildirimServisi() { }  // private constructor
public static BildirimServisi getInstance() {
    if (instance == null) {
        synchronized (BildirimServisi.class) {
            if (instance == null) { instance = new BildirimServisi(); }
        }
    }
    return instance;
}
```

---

## Faz 2 — Structural Örüntüler
_Henüz uygulanmadı._

## Faz 3 — Behavioral Örüntüler
_Henüz uygulanmadı._
