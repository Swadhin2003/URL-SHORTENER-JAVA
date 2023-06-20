import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
public class UserInputUrlShortener 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the long URL: ");
        String longUrl = scanner.nextLine();
        try 
        {
            String shortUrl = shortenUrl(longUrl);
            System.out.println("Long URL: " + longUrl);
            System.out.println("Short URL: " + shortUrl);
        } 
        catch (IOException e) 
        {
            System.out.println("Error occurred while shortening the URL: " + e.getMessage());
        }
        scanner.close();
    }
    private static String shortenUrl(String longUrl) throws IOException 
    {
        String apiUrl = "https://api.tinyurl.com/dev/api-create.php?url=" + URLEncoder.encode(longUrl, "UTF-8");
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        String shortUrl;
        if (responseCode == HttpURLConnection.HTTP_OK) 
        {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) 
            {
                shortUrl = br.readLine();
            }
        } 
        else 
        {
            throw new IOException("Failed to shorten URL. Response code: " + responseCode);
        }
        connection.disconnect();
        return shortUrl;
    }
}