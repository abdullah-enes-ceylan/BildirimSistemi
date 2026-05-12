/**
 * E-posta bildirimi — Bildirim arayüzünün somut implementasyonu.
 * 
 * E-posta gönderme, formatlama ve doğrulama mantığını kapsüller.
 * Artık bu mantık God Class'tan ayrılmıştır.
 */
public class EmailBildirim implements Bildirim {

    private String smtpSunucu;
    private int smtpPort;
    private String gonderenAdres;

    public EmailBildirim(String smtpSunucu, int smtpPort, String gonderenAdres) {
        this.smtpSunucu = smtpSunucu;
        this.smtpPort = smtpPort;
        this.gonderenAdres = gonderenAdres;
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        if (!aliciDogrula(alici)) {
            System.out.println("HATA: Gecersiz e-posta adresi: " + alici);
            return false;
        }

        String formatlananMesaj = formatla(baslik, mesaj);

        System.out.println("[EMAIL] Gonderiliyor...");
        System.out.println("  SMTP: " + smtpSunucu + ":" + smtpPort);
        System.out.println("  Gonderen: " + gonderenAdres);
        System.out.println("  Alici: " + alici);

        if (oncelik > 3) {
            System.out.println("  ** ACIL BILDIRIM **");
        }

        System.out.println("  Icerik: " + formatlananMesaj);
        System.out.println("[EMAIL] Basariyla gonderildi.");
        return true;
    }

    @Override
    public String formatla(String baslik, String mesaj) {
        return "<html><body><h1>" + baslik + "</h1><p>" + mesaj + "</p></body></html>";
    }

    @Override
    public boolean aliciDogrula(String alici) {
        return alici != null && alici.contains("@") && alici.contains(".");
    }

    @Override
    public String getTipAdi() {
        return "EMAIL";
    }
}
