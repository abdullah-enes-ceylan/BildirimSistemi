import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BildirimServisi — Singleton + Observer + Strategy entegrasyonu.
 * 
 * Bildirim gönderim orkestrasyonu:
 * - Singleton: Tek instance garantisi
 * - Factory Method: Bildirim nesnesi yaratma (BildirimFactory)
 * - Observer: Gönderim olaylarını dinleyicilere dağıtma
 * - Strategy: Runtime'da değiştirilebilir filtreleme
 */
public class BildirimServisi {

    private static volatile BildirimServisi instance;

    private final List<String> bildirimGecmisi;
    private final BildirimOlayYoneticisi olayYoneticisi;
    private FiltrelemeStratejisi filtrelemeStratejisi;

    private BildirimServisi() {
        this.bildirimGecmisi = new ArrayList<>();
        this.olayYoneticisi = BildirimOlayYoneticisi.getInstance();
        this.filtrelemeStratejisi = new HepsiniGonderStratejisi(); // varsayılan
    }

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
     * Filtreleme stratejisini runtime'da değiştirir (Strategy Pattern).
     */
    public void setFiltrelemeStratejisi(FiltrelemeStratejisi strateji) {
        this.filtrelemeStratejisi = strateji;
        System.out.println("[SERVIS] Filtreleme stratejisi degistirildi: " + strateji.getAd());
    }

    /**
     * Aktif filtreleme stratejisini döndürür.
     */
    public FiltrelemeStratejisi getFiltrelemeStratejisi() {
        return filtrelemeStratejisi;
    }

    /**
     * Bildirim gönderir — Strategy ile filtreler, Observer ile bildirir.
     */
    public boolean bildirimGonder(BildirimTipi tip, String alici, String baslik, String mesaj, int oncelik) {
        Bildirim bildirim = BildirimFactory.bildirimOlustur(tip);

        // Strategy: Filtreleme kontrolü
        if (!filtrelemeStratejisi.gonderilsinMi(bildirim.getTipAdi(), alici, oncelik)) {
            olayYoneticisi.basarisizGonderimBildir(bildirim.getTipAdi(), alici, baslik, "Filtreleme engelledi");
            return false;
        }

        System.out.println("=== Bildirim Gonderiliyor ===");
        System.out.println("Tip: " + bildirim.getTipAdi());
        System.out.println("Alici: " + alici);

        boolean sonuc = bildirim.gonder(alici, baslik, mesaj, oncelik);

        // Observer: Olayı dinleyicilere bildir
        if (sonuc) {
            gecmiseKaydet(bildirim.getTipAdi(), alici, baslik, true);
            olayYoneticisi.basariliGonderimBildir(bildirim.getTipAdi(), alici, baslik);
        } else {
            olayYoneticisi.basarisizGonderimBildir(bildirim.getTipAdi(), alici, baslik, "Gonderim basarisiz");
        }

        return sonuc;
    }

    /**
     * Toplu bildirim gönderir.
     */
    public void topluBildirimGonder(BildirimTipi tip, List<String> alicilar, String baslik, String mesaj, int oncelik) {
        System.out.println("\n=== TOPLU BILDIRIM ===");
        System.out.println("Toplam alici: " + alicilar.size());

        int basarili = 0;
        int basarisiz = 0;

        for (String alici : alicilar) {
            boolean sonuc = bildirimGonder(tip, alici, baslik, mesaj, oncelik);
            if (sonuc) basarili++;
            else basarisiz++;
        }

        System.out.println("Toplu gonderim tamamlandi: " + basarili + " basarili, " + basarisiz + " basarisiz");
    }

    private void gecmiseKaydet(String tip, String alici, String baslik, boolean basarili) {
        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String kayit = tarih + " | " + tip + " | " + alici + " | " + baslik + " | " +
                       (basarili ? "BASARILI" : "BASARISIZ");
        bildirimGecmisi.add(kayit);
    }

    public List<String> gecmisGetir(String filtreTip) {
        if (filtreTip == null || filtreTip.isEmpty()) {
            return new ArrayList<>(bildirimGecmisi);
        }
        List<String> filtrelenmis = new ArrayList<>();
        for (String kayit : bildirimGecmisi) {
            if (kayit.contains(filtreTip)) filtrelenmis.add(kayit);
        }
        return filtrelenmis;
    }

    public void istatistikYazdir() {
        int emailSayisi = 0, smsSayisi = 0, pushSayisi = 0;
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
