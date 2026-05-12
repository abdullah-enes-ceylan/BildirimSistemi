import java.util.ArrayList;
import java.util.List;

/**
 * BildirimOlayYoneticisi — Observer örüntüsünün Subject'i.
 * 
 * Dinleyicileri (observer) kaydeder ve bildirim olaylarında
 * tüm kayıtlı dinleyicilere haber verir.
 * 
 * Singleton olarak tasarlanmıştır — tüm uygulama tek bir
 * olay yöneticisi paylaşır.
 */
public class BildirimOlayYoneticisi {

    private static volatile BildirimOlayYoneticisi instance;
    private final List<BildirimOlayDinleyici> dinleyiciler;

    private BildirimOlayYoneticisi() {
        this.dinleyiciler = new ArrayList<>();
    }

    public static BildirimOlayYoneticisi getInstance() {
        if (instance == null) {
            synchronized (BildirimOlayYoneticisi.class) {
                if (instance == null) {
                    instance = new BildirimOlayYoneticisi();
                }
            }
        }
        return instance;
    }

    /**
     * Yeni bir dinleyici kaydeder.
     * OCP: Yeni dinleyici eklemek mevcut kodu değiştirmez.
     */
    public void dinleyiciEkle(BildirimOlayDinleyici dinleyici) {
        if (!dinleyiciler.contains(dinleyici)) {
            dinleyiciler.add(dinleyici);
            System.out.println("[OLAY] Dinleyici eklendi: " + dinleyici.getClass().getSimpleName());
        }
    }

    /**
     * Bir dinleyiciyi kaldırır.
     */
    public void dinleyiciKaldir(BildirimOlayDinleyici dinleyici) {
        dinleyiciler.remove(dinleyici);
    }

    /**
     * Başarılı gönderim olayını tüm dinleyicilere bildirir.
     */
    public void basariliGonderimBildir(String tip, String alici, String baslik) {
        for (BildirimOlayDinleyici dinleyici : dinleyiciler) {
            dinleyici.bildirimGonderildi(tip, alici, baslik);
        }
    }

    /**
     * Başarısız gönderim olayını tüm dinleyicilere bildirir.
     */
    public void basarisizGonderimBildir(String tip, String alici, String baslik, String sebep) {
        for (BildirimOlayDinleyici dinleyici : dinleyiciler) {
            dinleyici.bildirimBasarisiz(tip, alici, baslik, sebep);
        }
    }

    /**
     * Kayıtlı dinleyici sayısını döndürür.
     */
    public int dinleyiciSayisi() {
        return dinleyiciler.size();
    }
}
