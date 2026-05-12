/**
 * Bildirim arayüzü — tüm bildirim tiplerinin ortak sözleşmesi.
 * 
 * Factory Method örüntüsünün temelini oluşturur.
 * Her bildirim tipi bu arayüzü implement ederek kendi
 * gönderme, formatlama ve doğrulama mantığını taşır.
 */
public interface Bildirim {

    /**
     * Bildirimi gönderir.
     * @param alici   bildirimin gönderileceği adres/numara/token
     * @param baslik  bildirim başlığı
     * @param mesaj   bildirim içeriği
     * @param oncelik öncelik seviyesi (1-5)
     * @return gönderim başarılıysa true
     */
    boolean gonder(String alici, String baslik, String mesaj, int oncelik);

    /**
     * Mesajı ilgili kanal formatına dönüştürür.
     * @param baslik bildirim başlığı
     * @param mesaj  bildirim içeriği
     * @return formatlanmış mesaj
     */
    String formatla(String baslik, String mesaj);

    /**
     * Alıcı adresinin geçerliliğini kontrol eder.
     * @param alici kontrol edilecek adres
     * @return geçerliyse true
     */
    boolean aliciDogrula(String alici);

    /**
     * Bildirim tipinin adını döndürür.
     * @return tip adı (örn. "EMAIL", "SMS", "PUSH")
     */
    String getTipAdi();
}
