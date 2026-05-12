import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LoglamaDecorator — Bildirim gönderimini loglar.
 * 
 * Gönderim öncesi ve sonrasında detaylı log bilgisi yazdırır.
 * Mevcut bildirim sınıflarını değiştirmeden loglama ekler.
 */
public class LoglamaDecorator extends BildirimDecorator {

    public LoglamaDecorator(Bildirim bildirim) {
        super(bildirim);
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        String zaman = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("[LOG " + zaman + "] Bildirim gonderimi baslatiliyor...");
        System.out.println("[LOG] Tip: " + getTipAdi() + " | Alici: " + alici + " | Oncelik: " + oncelik);

        long baslangic = System.currentTimeMillis();
        boolean sonuc = super.gonder(alici, baslik, mesaj, oncelik);
        long sure = System.currentTimeMillis() - baslangic;

        System.out.println("[LOG " + zaman + "] Sonuc: " + (sonuc ? "BASARILI" : "BASARISIZ") + " | Sure: " + sure + "ms");
        return sonuc;
    }
}
