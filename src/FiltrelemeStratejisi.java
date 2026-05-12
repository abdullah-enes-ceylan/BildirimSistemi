/**
 * FiltrelemeStratejisi — Strategy örüntüsünün arayüzü.
 * 
 * Bildirimlerin gönderilip gönderilmeyeceğini belirleyen
 * filtreleme algoritmasını tanımlar. Runtime'da farklı
 * stratejiler değiştirilebilir.
 * 
 * OCP: Yeni bir filtreleme kuralı eklemek için sadece
 * bu arayüzü implement eden yeni bir sınıf yazmak yeterli.
 */
public interface FiltrelemeStratejisi {

    /**
     * Bildirimin gönderilmesine izin verilip verilmeyeceğini belirler.
     * @param tip     bildirim tipi
     * @param alici   alıcı adresi
     * @param oncelik öncelik seviyesi (1-5)
     * @return true ise bildirim gönderilir, false ise engellenir
     */
    boolean gonderilsinMi(String tip, String alici, int oncelik);

    /**
     * Stratejinin adını döndürür.
     */
    String getAd();
}
