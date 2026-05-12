/**
 * BildirimOlayDinleyici — Observer örüntüsünün arayüzü.
 * 
 * Bildirim gönderiminin sonucunu dinlemek isteyen sınıflar
 * bu arayüzü implement eder. Yeni bir dinleyici eklemek için
 * mevcut kodu değiştirmeye gerek yoktur (OCP).
 */
public interface BildirimOlayDinleyici {

    /**
     * Bildirim başarıyla gönderildiğinde çağrılır.
     * @param tip     bildirim tipi (EMAIL, SMS, PUSH)
     * @param alici   alıcı adresi
     * @param baslik  bildirim başlığı
     */
    void bildirimGonderildi(String tip, String alici, String baslik);

    /**
     * Bildirim gönderimi başarısız olduğunda çağrılır.
     * @param tip     bildirim tipi
     * @param alici   alıcı adresi
     * @param baslik  bildirim başlığı
     * @param sebep   başarısızlık nedeni
     */
    void bildirimBasarisiz(String tip, String alici, String baslik, String sebep);
}
