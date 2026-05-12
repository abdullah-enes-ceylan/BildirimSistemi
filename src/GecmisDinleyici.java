import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * GecmisDinleyici — Observer örüntüsünün somut dinleyicisi.
 * 
 * Bildirim olaylarını dinleyerek otomatik geçmiş kaydı tutar.
 * BildirimServisi'ndeki geçmiş yönetimi bu dinleyiciye taşınabilir.
 */
public class GecmisDinleyici implements BildirimOlayDinleyici {

    private final List<String> gecmis = new ArrayList<>();

    @Override
    public void bildirimGonderildi(String tip, String alici, String baslik) {
        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        gecmis.add(tarih + " | " + tip + " | " + alici + " | " + baslik + " | BASARILI");
    }

    @Override
    public void bildirimBasarisiz(String tip, String alici, String baslik, String sebep) {
        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        gecmis.add(tarih + " | " + tip + " | " + alici + " | " + baslik + " | BASARISIZ | " + sebep);
    }

    /**
     * Tüm geçmişi döndürür.
     */
    public List<String> gecmisGetir() {
        return new ArrayList<>(gecmis);
    }

    /**
     * Geçmişi yazdırır.
     */
    public void gecmisYazdir() {
        System.out.println("\n=== OBSERVER GECMISI ===");
        if (gecmis.isEmpty()) {
            System.out.println("  (kayit yok)");
        }
        for (String kayit : gecmis) {
            System.out.println("  " + kayit);
        }
    }
}
