/**
 * SMS bildirimi — Bildirim arayüzünün somut implementasyonu.
 * 
 * SMS gönderme, 160 karakter formatlama ve telefon numarası
 * doğrulama mantığını kapsüller.
 */
public class SmsBildirim implements Bildirim {

    private static final int SMS_KARAKTER_LIMITI = 160;

    private String apiAnahtari;
    private String gonderenNumara;
    private String apiUrl;

    public SmsBildirim(String apiAnahtari, String gonderenNumara, String apiUrl) {
        this.apiAnahtari = apiAnahtari;
        this.gonderenNumara = gonderenNumara;
        this.apiUrl = apiUrl;
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        if (!aliciDogrula(alici)) {
            System.out.println("HATA: Gecersiz telefon numarasi: " + alici);
            return false;
        }

        String formatlananMesaj = formatla(baslik, mesaj);

        if (oncelik > 3) {
            formatlananMesaj = "[ACIL] " + formatlananMesaj;
        }

        System.out.println("[SMS] Gonderiliyor...");
        System.out.println("  API: " + apiUrl);
        System.out.println("  Gonderen: " + gonderenNumara);
        System.out.println("  Alici: " + alici);
        System.out.println("  Icerik: " + formatlananMesaj);
        System.out.println("[SMS] Basariyla gonderildi.");
        return true;
    }

    @Override
    public String formatla(String baslik, String mesaj) {
        String tamMesaj = baslik + ": " + mesaj;
        if (tamMesaj.length() > SMS_KARAKTER_LIMITI) {
            tamMesaj = tamMesaj.substring(0, SMS_KARAKTER_LIMITI - 3) + "...";
            System.out.println("  UYARI: SMS " + SMS_KARAKTER_LIMITI + " karaktere kirpildi.");
        }
        return tamMesaj;
    }

    @Override
    public boolean aliciDogrula(String alici) {
        return alici != null && alici.length() >= 10 && alici.startsWith("+");
    }

    @Override
    public String getTipAdi() {
        return "SMS";
    }
}
