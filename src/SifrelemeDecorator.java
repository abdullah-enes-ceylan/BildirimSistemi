import java.util.Base64;

/**
 * SifrelemeDecorator — Mesaj içeriğini şifreleyerek gönderir.
 * 
 * Bildirim gönderilmeden önce mesaj içeriğini Base64 ile encode eder.
 * Gerçek uygulamada AES/RSA gibi güçlü şifreleme kullanılır,
 * burada Base64 demonstrasyon amaçlıdır.
 */
public class SifrelemeDecorator extends BildirimDecorator {

    public SifrelemeDecorator(Bildirim bildirim) {
        super(bildirim);
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        String sifrelenmis = sifrele(mesaj);
        System.out.println("[SIFRELEME] Mesaj sifrelendi. (" + mesaj.length() + " -> " + sifrelenmis.length() + " karakter)");
        return super.gonder(alici, baslik, sifrelenmis, oncelik);
    }

    @Override
    public String formatla(String baslik, String mesaj) {
        String sifrelenmis = sifrele(mesaj);
        return super.formatla(baslik, sifrelenmis);
    }

    private String sifrele(String metin) {
        return Base64.getEncoder().encodeToString(metin.getBytes());
    }
}
