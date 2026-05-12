/**
 * TekrarDenemeDecorator — Başarısız gönderimde otomatik tekrar dener.
 * 
 * Bildirim gönderimi başarısız olursa, belirli sayıda tekrar dener.
 * Her denemeden önce bekleme süresi uygular (basit backoff).
 * Mevcut bildirim sınıflarını değiştirmeden retry mekanizması ekler.
 */
public class TekrarDenemeDecorator extends BildirimDecorator {

    private final int maxDeneme;
    private final long beklemeMs;

    /**
     * @param bildirim  sarmalanacak bildirim
     * @param maxDeneme maksimum deneme sayısı (ilk deneme dahil)
     * @param beklemeMs denemeler arası bekleme süresi (milisaniye)
     */
    public TekrarDenemeDecorator(Bildirim bildirim, int maxDeneme, long beklemeMs) {
        super(bildirim);
        this.maxDeneme = maxDeneme;
        this.beklemeMs = beklemeMs;
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        for (int deneme = 1; deneme <= maxDeneme; deneme++) {
            System.out.println("[RETRY] Deneme " + deneme + "/" + maxDeneme);

            boolean sonuc = super.gonder(alici, baslik, mesaj, oncelik);

            if (sonuc) {
                return true;
            }

            if (deneme < maxDeneme) {
                System.out.println("[RETRY] Basarisiz. " + beklemeMs + "ms sonra tekrar denenecek...");
                try {
                    Thread.sleep(beklemeMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }

        System.out.println("[RETRY] Tum denemeler basarisiz oldu!");
        return false;
    }
}
