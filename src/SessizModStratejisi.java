import java.util.HashSet;
import java.util.Set;

/**
 * SessizModStratejisi — Belirli bildirim tiplerini engeller.
 * 
 * Kullanıcı "sessiz mod"a geçtiğinde push bildirimlerini engelleyip
 * sadece e-posta/SMS'e izin vermek gibi senaryolarda kullanılır.
 * Runtime'da hangi tiplerin engellendiği değiştirilebilir.
 */
public class SessizModStratejisi implements FiltrelemeStratejisi {

    private final Set<String> engellenenTipler;

    public SessizModStratejisi() {
        this.engellenenTipler = new HashSet<>();
    }

    /**
     * Bir bildirim tipini engeller.
     */
    public void tipEngelle(String tip) {
        engellenenTipler.add(tip);
    }

    /**
     * Bir bildirim tipinin engelini kaldırır.
     */
    public void tipIzinVer(String tip) {
        engellenenTipler.remove(tip);
    }

    @Override
    public boolean gonderilsinMi(String tip, String alici, int oncelik) {
        // Acil bildirimler (oncelik > 4) sessiz modda bile gecebilir
        if (oncelik > 4) {
            return true;
        }

        if (engellenenTipler.contains(tip)) {
            System.out.println("[FILTRE] Sessiz mod: " + tip + " bildirimi engellendi");
            return false;
        }
        return true;
    }

    @Override
    public String getAd() {
        return "SESSIZ_MOD (engellenen: " + engellenenTipler + ")";
    }
}
