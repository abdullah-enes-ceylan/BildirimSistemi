# Bildirim Sistemi — Evrimleşen Sistem Ödevi

## Konu Seçimi: A — Bildirim Sistemi

**Gerekçe:** Bildirim Sistemi konusunu seçtim çünkü gerçek dünya uygulamalarında bildirim yönetimi çok yaygın bir ihtiyaçtır. E-posta, SMS ve push bildirimlerinin tek bir sınıfta yönetilmesi, tasarım örüntülerinin neden gerekli olduğunu somut bir şekilde gösterir. Ayrıca bu konu, Creational (nesne yaratma), Structural (yapısal genişletme) ve Behavioral (davranışsal esneklik) örüntülerinin üçünü de doğal olarak uygulamaya uygundur.

## Proje Açıklaması

Bu proje, bir bildirim sisteminin evrimini gösterir. Başlangıçta tüm bildirim tipleri (e-posta, SMS, push) tek bir "God Class" içinde if-else zincirleriyle yönetilmektedir. Üç faz boyunca tasarım örüntüleri uygulanarak sistem adım adım profesyonel bir mimariye dönüştürülecektir.

## Kullanılan Teknolojiler

- **Dil:** Java 21 (Microsoft OpenJDK)
- **Versiyon Kontrolü:** Git & GitHub

## Nasıl Çalıştırılır

```bash
# Derleme
javac -encoding UTF-8 -d out src/*.java

# Çalıştırma
java -cp out Main
```

## Proje Yapısı

```
BildirimSistemi/
├── README.md                  ← Bu dosya
├── PATTERNS.md               ← Uygulanan örüntüler
├── PROBLEMS.md               ← Başlangıç kodu analizi
├── src/                       ← Kaynak kod
│   ├── BildirimServisi.java  ← Ana servis sınıfı
│   └── Main.java             ← Demo/test uygulaması
├── docs/
│   ├── diagrams/              ← UML diyagramları
│   └── ai-log/
│       ├── phase1.md
│       ├── phase2.md
│       └── phase3.md
└── .github/workflows/ci.yml  ← GitHub Actions
```

## Fazlar

| Faz | Açıklama | Durum |
|-----|----------|-------|
| Faz 0 | Başlangıç kodu & problem analizi | ✅ Tamamlandı |
| Faz 1 | Creational örüntüler | 🔲 Bekliyor |
| Faz 2 | Structural örüntüler | 🔲 Bekliyor |
| Faz 3 | Behavioral örüntüler | 🔲 Bekliyor |
