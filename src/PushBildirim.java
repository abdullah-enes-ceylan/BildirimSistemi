/**
 * Push bildirimi — Bildirim arayüzünün somut implementasyonu.
 * 
 * Mobil push bildirim gönderme, JSON payload formatlama
 * ve cihaz token doğrulama mantığını kapsüller.
 */
public class PushBildirim implements Bildirim {

    private static final int MIN_TOKEN_UZUNLUK = 20;

    private String apiAnahtari;
    private String sunucuUrl;
    private String uygulamaId;

    public PushBildirim(String apiAnahtari, String sunucuUrl, String uygulamaId) {
        this.apiAnahtari = apiAnahtari;
        this.sunucuUrl = sunucuUrl;
        this.uygulamaId = uygulamaId;
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        if (!aliciDogrula(alici)) {
            System.out.println("HATA: Gecersiz cihaz token'i: " + alici);
            return false;
        }

        String formatlananMesaj = formatla(baslik, mesaj);

        System.out.println("[PUSH] Gonderiliyor...");
        System.out.println("  Sunucu: " + sunucuUrl);
        System.out.println("  Uygulama: " + uygulamaId);
        System.out.println("  Token: " + alici);

        if (oncelik > 3) {
            System.out.println("  Ses: critical | Badge: 1");
        }

        System.out.println("  Payload: " + formatlananMesaj);
        System.out.println("[PUSH] Basariyla gonderildi.");
        return true;
    }

    @Override
    public String formatla(String baslik, String mesaj) {
        return "{\"title\":\"" + baslik + "\",\"body\":\"" + mesaj + "\"}";
    }

    @Override
    public boolean aliciDogrula(String alici) {
        return alici != null && alici.length() >= MIN_TOKEN_UZUNLUK;
    }

    @Override
    public String getTipAdi() {
        return "PUSH";
    }
}
