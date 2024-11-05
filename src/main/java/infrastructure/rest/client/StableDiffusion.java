package infrastructure.rest.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey="stable-diffusion-api")
public interface StableDiffusion {

    @POST
    @Path("/sdapi/v1/img2img")
    Uni<Response> img2img(Request request);

    record Request(
            @JsonProperty("init_images") List<String> initImages,
            String prompt,
            @JsonProperty("negative_prompt") String negativePrompt,
            @JsonProperty("sampler_name") String samplerName,
            Integer seed,
            @JsonProperty("denoising_strength") Double denoisingStrength,
            @JsonProperty("cfg_scale") Double cfgScale,
            Integer steps,
            Integer width,
            Integer height) {
        public Request(String initImage, String prompt, String negativePrompt) {
            this(List.of(initImage),
                    prompt,
                    negativePrompt,
                    "Euler a",
                    -1,
                    0.9,
                    7.0,
                    20,
                    512,
                    512);
        }
    }

    record Response(List<String> images) {

    }
}
