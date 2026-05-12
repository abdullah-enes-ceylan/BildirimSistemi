import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BildirimServisi — Singleton örüntüsü ile tek instance garantisi.
 * 
 * Refactored: God Class yapısı kırıldı.
 * - Bildirim yaratma: BildirimFactory'ye devredildi (Factory Method)
 * - Bildirim davranışları: Her tip kendi sınıfında (Bildirim arayüzü)
 * - Bu sınıf artık sadece orkestrasyon ve geçmiş yönetiminden sorumlu
 */
public class BildirimServisi {

    // Thread-safe Singleton - private constructor + static holder
    private static volatile BildirimServisi instance;

    private final List<String> bildirimGecmisi;

    /**
     * Private constructor — dışarıdan new ile oluşturulamaz.
     */
    private BildirimServisi() {
        this.bildirimGecmisi = new ArrayList<>();
    }

    /**
     * Thread-safe Singleton erişimi (Double-Checked Locking).
     * @return BildirimServisi'nin tek instance'ı
     */
    public static BildirimServisi getInstance() {
        if (instance == null) {
            synchronized (BildirimServisi.class) {
                if (instance == null) {
                    instance = new BildirimServisi();
                }
            }
        }
        return instance;
    }

    /**
     * Bildirim gönderir — Factory Method ile nesne oluşturur, Bildirim arayüzü ile gönderir.
     * Artık if-else zinciri yok!
     */
    public boolean bildirimGonder(BildirimTipi tip, String alici, String baslik, String mesaj, int oncelik) {
        Bildirim bildirim = BildirimFactory.bildirimOlustur(tip);

        System.out.println("=== Bildirim Gonderiliyor ===");
        System.out.println("Tip: " + bildirim.getTipAdi());
        System.out.println("Alici: " + alici);

        boolean sonuc = bildirim.gonder(alici, baslik, mesaj, oncelik);

        if (sonuc) {
            gecmiseKaydet(bildirim.getTipAdi(), alici, baslik, true);
        }

        return sonuc;
    }

    /**
     * Toplu bildirim gönderir — tüm alıcılara aynı tipte bildirim.
     */
    public void topluBildirimGonder(BildirimTipi tip, List<String> alicilar, String baslik, String mesaj, int oncelik) {
        System.out.println("\n=== TOPLU BILDIRIM ===");
        System.out.println("Toplam alici: " + alicilar.size());

        int basarili = 0;
        int basarisiz = 0;

        for (String alici : alicilar) {
            boolean sonuc = bildirimGonder(tip, alici, baslik, mesaj, oncelik);
            if (sonuc) {
                basarili++;
            } else {
                basarisiz++;
            }
        }

        System.out.println("Toplu gonderim tamamlandi: " + basarili + " basarili, " + basarisiz + " basarisiz");
    }

    /**
     * Bildirim geçmişine kayıt ekler.
     */
    private void gecmiseKaydet(String tip, String alici, String baslik, boolean basarili) {
        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String kayit = tarih + " | " + tip + " | " + alici + " | " + baslik + " | " +
                       (basarili ? "BASARILI" : "BASARISIZ");
        bildirimGecmisi.add(kayit);
    }

    /**
     * Bildirim geçmişini filtreli/filtresiz getirir.
     */
    public List<String> gecmisGetir(String filtreTip) {
        if (filtreTip == null || filtreTip.isEmpty()) {
            return new ArrayList<>(bildirimGecmisi);
        }

        List<String> filtrelenmis = new ArrayList<>();
        for (String kayit : bildirimGecmisi) {
            if (kayit.contains(filtreTip)) {
                filtrelenmis.add(kayit);
            }
        }
        return filtrelenmis;
    }

    /**
     * Bildirim istatistiklerini yazdırır.
     */
    public void istatistikYazdir() {
        int emailSayisi = 0;
        int smsSayisi = 0;
        int pushSayisi = 0;

        for (String kayit : bildirimGecmisi) {
            if (kayit.contains("EMAIL")) emailSayisi++;
            else if (kayit.contains("SMS")) smsSayisi++;
            else if (kayit.contains("PUSH")) pushSayisi++;
        }

        System.out.println("\n=== BILDIRIM ISTATISTIKLERI ===");
        System.out.println("E-posta: " + emailSayisi);
        System.out.println("SMS: " + smsSayisi);
        System.out.println("Push: " + pushSayisi);
        System.out.println("Toplam: " + (emailSayisi + smsSayisi + pushSayisi));
    }
}
