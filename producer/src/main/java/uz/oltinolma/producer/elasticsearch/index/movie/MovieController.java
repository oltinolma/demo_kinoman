package uz.oltinolma.producer.elasticsearch.index.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oltinolma.producer.common.BaseResponses;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

@RestController
@RequestMapping("/elastic/movie")
public class MovieController {
    @Autowired
    private BaseResponses baseResponses;
    @Autowired
    private MovieService movieService;

    @PreAuthorize("@SecurityPermission.hasPermission('movie.index')")
    @RequestMapping("/index/all")
    public BaseResponse indexAllMerchants() {
        movieService.indexAll();
        return baseResponses.successMessage();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('movie.index')")
    @PostMapping("/index")
    public BaseResponse index(@RequestBody Movie movie) {
        movieService.index(movie);
        return baseResponses.successMessage();
    }
}
