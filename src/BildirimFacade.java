import java.util.List;

/**
 * BildirimFacade — Facade örüntüsü.
 * 
 * Bildirim sisteminin karmaşıklığını (Factory, Decorator, Singleton)
 * gizleyerek istemci koduna basit ve temiz bir arayüz sunar.
 * 
 * İstemci kodu artık şunları bilmek zorunda değil:
 * - BildirimFactory nasıl çalışır
 * - Hangi Decorator'lar uygulanmalı
 * - BildirimTipi enum'u nedir
 * - BildirimServisi Singleton nasıl alınır
 * 
 * Tek satırla bildirim gönderebilir:
 *   facade.emailGonder("alici@test.com", "Başlık", "Mesaj");
 */
public class BildirimFacade {

    private final BildirimServisi servis;
    private final boolean loglamaAktif;
    private final boolean sifrelemeAktif;

    /**
     * Varsayılan facade — loglama açık, şifreleme kapalı.
     */
    public BildirimFacade() {
        this(true, false);
    }

    /**
     * Özelleştirilebilir facade.
     * @param loglamaAktif  loglama decorator'ü aktif mi
     * @param sifrelemeAktif şifreleme decorator'ü aktif mi
     */
    public BildirimFacade(boolean loglamaAktif, boolean sifrelemeAktif) {
        this.servis = BildirimServisi.getInstance();
        this.loglamaAktif = loglamaAktif;
        this.sifrelemeAktif = sifrelemeAktif;
    }

    /**
     * E-posta bildirimi gönderir (normal öncelik).
     */
    public boolean emailGonder(String alici, String baslik, String mesaj) {
        return emailGonder(alici, baslik, mesaj, 1);
    }

    /**
     * E-posta bildirimi gönderir (özelleştirilebilir öncelik).
     */
    public boolean emailGonder(String alici, String baslik, String mesaj, int oncelik) {
        Bildirim bildirim = hazirla(BildirimTipi.EMAIL);
        return bildirimGonderVeKaydet(bildirim, alici, baslik, mesaj, oncelik);
    }

    /**
     * SMS bildirimi gönderir (normal öncelik).
     */
    public boolean smsGonder(String alici, String baslik, String mesaj) {
        return smsGonder(alici, baslik, mesaj, 1);
    }

    /**
     * SMS bildirimi gönderir (özelleştirilebilir öncelik).
     */
    public boolean smsGonder(String alici, String baslik, String mesaj, int oncelik) {
        Bildirim bildirim = hazirla(BildirimTipi.SMS);
        return bildirimGonderVeKaydet(bildirim, alici, baslik, mesaj, oncelik);
    }

    /**
     * Push bildirimi gönderir (normal öncelik).
     */
    public boolean pushGonder(String alici, String baslik, String mesaj) {
        return pushGonder(alici, baslik, mesaj, 1);
    }

    /**
     * Push bildirimi gönderir (özelleştirilebilir öncelik).
     */
    public boolean pushGonder(String alici, String baslik, String mesaj, int oncelik) {
        Bildirim bildirim = hazirla(BildirimTipi.PUSH);
        return bildirimGonderVeKaydet(bildirim, alici, baslik, mesaj, oncelik);
    }

    /**
     * Acil bildirim gönderir — otomatik olarak yüksek öncelik (5).
     */
    public boolean acilBildirimGonder(BildirimTipi tip, String alici, String baslik, String mesaj) {
        Bildirim bildirim = hazirla(tip);
        // Acil bildirimler retry ile sarmalanır
        Bildirim retryBildirim = new TekrarDenemeDecorator(bildirim, 3, 1000);
        return bildirimGonderVeKaydet(retryBildirim, alici, baslik, mesaj, 5);
    }

    /**
     * Toplu e-posta gönderir.
     */
    public void topluEmailGonder(List<String> alicilar, String baslik, String mesaj) {
        servis.topluBildirimGonder(BildirimTipi.EMAIL, alicilar, baslik, mesaj, 1);
    }

    /**
     * Toplu SMS gönderir.
     */
    public void topluSmsGonder(List<String> alicilar, String baslik, String mesaj) {
        servis.topluBildirimGonder(BildirimTipi.SMS, alicilar, baslik, mesaj, 1);
    }

    /**
     * İstatistikleri yazdırır.
     */
    public void istatistikleriGoster() {
        servis.istatistikYazdir();
    }

    /**
     * Bildirim geçmişini döndürür.
     */
    public List<String> gecmisGetir() {
        return servis.gecmisGetir(null);
    }

    // --- Yardımcı metotlar (private) ---

    /**
     * Bildirim nesnesini oluşturur ve yapılandırmaya göre decorator'ları uygular.
     */
    private Bildirim hazirla(BildirimTipi tip) {
        Bildirim bildirim = BildirimFactory.bildirimOlustur(tip);

        if (sifrelemeAktif) {
            bildirim = new SifrelemeDecorator(bildirim);
        }

        if (loglamaAktif) {
            bildirim = new LoglamaDecorator(bildirim);
        }

        return bildirim;
    }

    /**
     * Bildirimi gönderir ve servise kaydeder.
     */
    private boolean bildirimGonderVeKaydet(Bildirim bildirim, String alici, String baslik, String mesaj, int oncelik) {
        return bildirim.gonder(alici, baslik, mesaj, oncelik);
    }
}
