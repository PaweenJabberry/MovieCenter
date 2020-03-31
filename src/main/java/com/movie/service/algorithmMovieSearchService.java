package com.movie.service;

import com.movie.model.MovieData;
import com.movie.repositories.MovieRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("invertedIndexMovieSearchService")
@DependsOn("initializeData")
public class algorithmMovieSearchService implements MovieSearchService {

    private final MovieRepository movieRepository;

    private Map<String, List<Long>> index = new HashMap<String, List<Long>>();

    private ArrayList<Integer> allId = new ArrayList<>();

    public algorithmMovieSearchService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void createAlgorithm() {
        createInvertedIndex();
    }

    @Override
    public List<MovieData> searchByTitle(String keyword) {

        List<MovieData> movieDataList = invertedIndexSearch(keyword);

        return movieDataList;
    }

//    @Override
//    public long searchById(long id) {
//        return 0;
//    }
//
//    public void createBinarySearch() {
//    }
//
//    public long binarySearch() {
//        return 0;
//    }

    public void createInvertedIndex() {
        System.out.println("Create InvertedIndexSearch");

        //	Create Inverted Index
        Iterable<MovieData> movies = movieRepository.findAll();
        //	Split the word from title name to dictionary
        for (MovieData movie: movies) {
            String name = movie.getTitle();
            for (String wordSplit : name.split(" ")) {
                String word = wordSplit.toLowerCase();
                List<Long> idx = index.get(word);
                if (idx == null) {
                    idx = new LinkedList<>();
                    index.put(word,idx);
                }
                if (idx.contains(movie.getId()) == false) {
                    idx.add(movie.getId());
                }
            }
        }
    }

    public List<MovieData> invertedIndexSearch(String keyword) {
        List<MovieData> movieDataList = new ArrayList<>();

        try {
            List<Long> idHasWord = new ArrayList<>();
            String[] wordSplit = keyword.split(" ");
            //	Split queryText	for search in dictionary
            //	This method will give ArrayList of All MovieId that split of queryText is occur
            for (String currentWord: wordSplit) {
                List<Long> idx = index.get(currentWord.toLowerCase());
                if (idx != null) {
                    idHasWord.addAll(idx);
                } else {
//					System.out.println("Null");
//					System.out.println(queryText);
//					System.out.println(currentWord);
                }
            }
            Collections.sort(idHasWord);

            long countOccur = 0;
            long currentId = 0;;
            List<Long> idMustSearch = new ArrayList<>();
            //	Check the movie Id that queryText occur
            currentId = idHasWord.get(0);
            for(int i=0;i<idHasWord.size();i++) {
                if (currentId == idHasWord.get(i)) {
                    countOccur++;
                } else {
                    currentId = idHasWord.get(i);
                    countOccur = 1;
                }

                if (countOccur == wordSplit.length) {
                    idMustSearch.add(currentId);
                }
            }

            //	Actual search after know the scope of id for searching
            for (Long id:idMustSearch) {
                Optional<MovieData> movie = movieRepository.findById(id);
                movieDataList.add(movie.get());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDataList;
    }

}
