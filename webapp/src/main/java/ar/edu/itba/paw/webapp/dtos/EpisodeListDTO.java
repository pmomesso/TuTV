package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Episode;
import ar.edu.itba.paw.model.User;

import java.util.*;

public class EpisodeListDTO {

    private List<EpisodeDTO> episodes = Collections.emptyList();

    public EpisodeListDTO() {
        //Empty constructor for JAX-RS
    }

    public EpisodeListDTO(Collection<Episode> episodeColection, Optional<User> loggedInUser) {
        episodes = new ArrayList<>();
        episodeColection.stream().forEach(e -> episodes.add(new EpisodeDTO(e,loggedInUser)));
    }

    public List<EpisodeDTO> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeDTO> genres) {
        this.episodes = episodes;
    }
}
