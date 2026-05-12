/**
 * BildirimDecorator — Decorator örüntüsünün soyut temel sınıfı.
 * 
 * Bildirim arayüzünü implement eder ve bir Bildirim nesnesini sarar.
 * Alt sınıflar, sarılan bildirime ek davranışlar ekleyebilir
 * (loglama, tekrar deneme, şifreleme vb.) mevcut kodu değiştirmeden.
 * 
 * Bu örüntü Open/Closed Principle'ı (OCP) destekler:
 * Yeni bir davranış eklemek için yeni bir Decorator yazılır,
 * mevcut sınıflar değiştirilmez.
 */
public abstract class BildirimDecorator implements Bildirim {

    protected final Bildirim sarmalanmisBildirim;

    /**
     * @param bildirim sarmalanacak (dekore edilecek) bildirim nesnesi
     */
    public BildirimDecorator(Bildirim bildirim) {
        this.sarmalanmisBildirim = bildirim;
    }

    @Override
    public boolean gonder(String alici, String baslik, String mesaj, int oncelik) {
        return sarmalanmisBildirim.gonder(alici, baslik, mesaj, oncelik);
    }

    @Override
    public String formatla(String baslik, String mesaj) {
        return sarmalanmisBildirim.formatla(baslik, mesaj);
    }

    @Override
    public boolean aliciDogrula(String alici) {
        return sarmalanmisBildirim.aliciDogrula(alici);
    }

    @Override
    public String getTipAdi() {
        return sarmalanmisBildirim.getTipAdi();
    }
}
