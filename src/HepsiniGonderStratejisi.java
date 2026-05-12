/**
 * HepsiniGonderStratejisi — Tüm bildirimlere izin verir.
 * Varsayılan strateji; hiçbir filtreleme yapmaz.
 */
public class HepsiniGonderStratejisi implements FiltrelemeStratejisi {

    @Override
    public boolean gonderilsinMi(String tip, String alici, int oncelik) {
        return true;
    }

    @Override
    public String getAd() {
        return "HEPSINI_GONDER";
    }
}
