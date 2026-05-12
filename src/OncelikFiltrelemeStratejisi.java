/**
 * OncelikFiltrelemeStratejisi — Sadece belirli öncelik ve üstünü gönderir.
 * 
 * Örneğin minimumOncelik=3 ise, öncelik 1 ve 2 olan bildirimler
 * engellenir, 3+ gönderilir. Gece saatlerinde sadece acil
 * bildirimlerin gönderilmesi gibi senaryolarda kullanılır.
 */
public class OncelikFiltrelemeStratejisi implements FiltrelemeStratejisi {

    private final int minimumOncelik;

    /**
     * @param minimumOncelik gönderilecek minimum öncelik seviyesi (1-5)
     */
    public OncelikFiltrelemeStratejisi(int minimumOncelik) {
        this.minimumOncelik = minimumOncelik;
    }

    @Override
    public boolean gonderilsinMi(String tip, String alici, int oncelik) {
        if (oncelik < minimumOncelik) {
            System.out.println("[FILTRE] Bildirim engellendi: oncelik " + oncelik + " < minimum " + minimumOncelik);
            return false;
        }
        return true;
    }

    @Override
    public String getAd() {
        return "ONCELIK_FILTRE (min=" + minimumOncelik + ")";
    }
}
