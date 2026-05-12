import java.util.HashMap;
import java.util.Map;

/**
 * IstatistikDinleyici — Observer örüntüsünün somut dinleyicisi.
 * 
 * Bildirim olaylarını dinleyerek otomatik istatistik toplar.
 * BildirimServisi'ne dokunmadan istatistik toplama özelliği ekler (OCP).
 */
public class IstatistikDinleyici implements BildirimOlayDinleyici {

    private final Map<String, Integer> basariliSayilari = new HashMap<>();
    private final Map<String, Integer> basarisizSayilari = new HashMap<>();

    @Override
    public void bildirimGonderildi(String tip, String alici, String baslik) {
        basariliSayilari.merge(tip, 1, Integer::sum);
    }

    @Override
    public void bildirimBasarisiz(String tip, String alici, String baslik, String sebep) {
        basarisizSayilari.merge(tip, 1, Integer::sum);
    }

    /**
     * Toplanan istatistikleri yazdırır.
     */
    public void istatistikleriYazdir() {
        System.out.println("\n=== OBSERVER ISTATISTIKLERI ===");

        int toplamBasarili = 0;
        int toplamBasarisiz = 0;

        for (Map.Entry<String, Integer> entry : basariliSayilari.entrySet()) {
            int basarisiz = basarisizSayilari.getOrDefault(entry.getKey(), 0);
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " basarili, " + basarisiz + " basarisiz");
            toplamBasarili += entry.getValue();
            toplamBasarisiz += basarisiz;
        }

        // Sadece basarisiz olan tipleri de goster
        for (Map.Entry<String, Integer> entry : basarisizSayilari.entrySet()) {
            if (!basariliSayilari.containsKey(entry.getKey())) {
                System.out.println("  " + entry.getKey() + ": 0 basarili, " + entry.getValue() + " basarisiz");
                toplamBasarisiz += entry.getValue();
            }
        }

        System.out.println("  TOPLAM: " + toplamBasarili + " basarili, " + toplamBasarisiz + " basarisiz");
    }
}
