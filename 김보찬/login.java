
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class login{

    //로그인 요청 함수
    public void RequestLogin(String ID, String PW) throws InterruptedException
    {
        String baseURL = "";   //로그인 처리 URL 
        String Login_parameter = "?ID=" + ID + "&PW=" + Hashing(PW, ID+"team8fighting"); 
        String url = baseURL + Login_parameter;

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        final Call call = okHttpClient.newCall(request);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = call.execute();
                    String result = response.body().string();

                    //아직 응답을 어떻게 할지 안정함...

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();

    }

    //어플 측 패스워드 해싱 암호화 
    private String Hashing(String PW, String salt)
    {

    }
}