package com.ilham.mymoviecatalogue.items;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ilham.mymoviecatalogue.database.DatabaseContract;

import java.util.List;

import static android.provider.BaseColumns._ID;

public class Movie {

    /**
     * results : [{"vote_count":697,"id":420818,"video":false,"vote_average":7.2,"title":"The Lion King","popularity":523.663,"poster_path":"/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg","original_language":"en","original_title":"The Lion King","genre_ids":[12,16,10751,18,28],"backdrop_path":"/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg","adult":false,"overview":"Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother\u2014and former heir to the throne\u2014has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.","release_date":"2019-07-12"},{"vote_count":1717,"id":447404,"video":false,"vote_average":7,"title":"Pokémon Detective Pikachu","popularity":160.346,"poster_path":"/wgQ7APnFpf1TuviKHXeEe3KnsTV.jpg","original_language":"en","original_title":"Pokémon Detective Pikachu","genre_ids":[9648,10751,35,878,28,12],"backdrop_path":"/nDP33LmQwNsnPv29GQazz59HjJI.jpg","adult":false,"overview":"In a world where people collect pocket-size monsters (Pokémon) to do battle, a boy comes across an intelligent monster who seeks to be a detective.","release_date":"2019-05-03"},{"vote_count":21,"id":566555,"video":false,"vote_average":5,"title":"Detective Conan: The Fist of Blue Sapphire","popularity":157.672,"poster_path":"/86Y6qM8zTn3PFVfCm9J98Ph7JEB.jpg","original_language":"ja","original_title":"名探偵コナン 紺青の拳（フィスト）","genre_ids":[16,28,18,9648,35],"backdrop_path":"/wf6VDSi4aFCZfuD8sX8JAKLfJ5m.jpg","adult":false,"overview":"23rd movie in the \"Detective Conan\" franchise.","release_date":"2019-04-12"},{"vote_count":49,"id":458302,"video":false,"vote_average":7.6,"title":"Remi, Nobody's Boy","popularity":138.389,"poster_path":"/mQYXlxlUTmOP4FWt52qkZZb8JNM.jpg","original_language":"fr","original_title":"Rémi sans famille","genre_ids":[18,35],"backdrop_path":"/4aFtD31RpP018y8XipTLrU8Y3pa.jpg","adult":false,"overview":"The adventures of the young Rémi, an orphan, collected by the gentle Madam Barberin. At the age of 10 years, he is snatched from his adoptive mother and entrusted to the signor Vitalis, a mysterious itinerant musician. Has its sides, he will learn the harsh life of acrobat and sing to win his bread. Accompanied by the faithful dog capi and of the small monkey Joli-Coeur, his long trip through France, made for meetings, friendships and mutual assistance, leads him to the secret of its origins.","release_date":"2018-12-12"},{"vote_count":1535,"id":301528,"video":false,"vote_average":7.7,"title":"Toy Story 4","popularity":131.381,"poster_path":"/w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg","original_language":"en","original_title":"Toy Story 4","genre_ids":[12,16,35,10751],"backdrop_path":"/m67smI1IIMmYzCl9axvKNULVKLr.jpg","adult":false,"overview":"Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.","release_date":"2019-06-19"},{"vote_count":535,"id":479455,"video":false,"vote_average":5.9,"title":"Men in Black: International","popularity":121.924,"poster_path":"/dPrUPFcgLfNbmDL8V69vcrTyEfb.jpg","original_language":"en","original_title":"Men in Black: International","genre_ids":[28,35,878,12],"backdrop_path":"/2FYzxgLNuNVwncilzUeCGbOQzP7.jpg","adult":false,"overview":"The Men in Black have always protected the Earth from the scum of the universe. In this new adventure, they tackle their biggest, most global threat to date: a mole in the Men in Black organization.","release_date":"2019-06-12"},{"vote_count":213,"id":459992,"video":false,"vote_average":6.9,"title":"Long Shot","popularity":108.118,"poster_path":"/m2ttWZ8rMRwIMT7zA48Jo6mTkDS.jpg","original_language":"en","original_title":"Long Shot","genre_ids":[35,10749],"backdrop_path":"/88r25ghJzVYKq0vaOApqEOZsQlD.jpg","adult":false,"overview":"When Fred Flarsky reunites with and charms his first crush, Charlotte Field\u2014one of the most influential women in the world. As Charlotte prepares to make a run for the Presidency, she hires Fred as her speechwriter and sparks fly.","release_date":"2019-05-02"},{"vote_count":2,"id":486589,"video":false,"vote_average":0,"title":"Red Shoes and the Seven Dwarfs","popularity":80.21,"poster_path":"/xQccIXfq9J4tgbvdSSPPLLYZGRD.jpg","original_language":"en","original_title":"Red Shoes and the Seven Dwarfs","genre_ids":[16,10749],"backdrop_path":"/4uhVMTAgGt36h68SCTOQKCB4z50.jpg","adult":false,"overview":"Princes who have been turned into Dwarfs seek the red shoes of a lady in order to break the spell, although it will not be easy.","release_date":"2019-07-25"},{"vote_count":98,"id":511987,"video":false,"vote_average":5.8,"title":"Crawl","popularity":65.535,"poster_path":"/mKxpYRIrCZLxZjNqpocJ2RdQW8v.jpg","original_language":"en","original_title":"Crawl","genre_ids":[53,28,27],"backdrop_path":"/2cAce3oD0Hh7f5XxuXmNXa1WiuZ.jpg","adult":false,"overview":"While struggling to save her father during a Category 5 hurricane, a young woman finds herself trapped inside a flooding house and fighting for her life against Florida\u2019s most savage and feared predators.","release_date":"2019-07-11"},{"vote_count":15,"id":466272,"video":false,"vote_average":10,"title":"Once Upon a Time in Hollywood","popularity":55.973,"poster_path":"/8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg","original_language":"en","original_title":"Once Upon a Time in Hollywood","genre_ids":[18,35,28,80,37],"backdrop_path":"/b82nVVhYNRgtsTFV1jkdDwe6LJZ.jpg","adult":false,"overview":"A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles.","release_date":"2019-07-25"},{"vote_count":46,"id":524247,"video":false,"vote_average":6.6,"title":"The Intruder","popularity":53.413,"poster_path":"/p9xKyetr0ihJ2K6HJMeXzc4IwEv.jpg","original_language":"en","original_title":"The Intruder","genre_ids":[53,18],"backdrop_path":"/kyegg1r5oxRCZgqU7anHYD2TJap.jpg","adult":false,"overview":"A psychological thriller about a young married couple who buys a beautiful Napa Valley house on several acres of land only to find that the man they bought it from refuses to let go of the property.","release_date":"2019-05-03"},{"vote_count":1,"id":384018,"video":false,"vote_average":0,"title":"Fast & Furious Presents: Hobbs & Shaw","popularity":44.408,"poster_path":"/keym7MPn1icW1wWfzMnW3HeuzWU.jpg","original_language":"en","original_title":"Fast & Furious Presents: Hobbs & Shaw","genre_ids":[28],"backdrop_path":"/fgPZgeqxDKIw86pBiAyLhh0vTrU.jpg","adult":false,"overview":"A spinoff of The Fate of the Furious, focusing on Johnson's US Diplomatic Security Agent Luke Hobbs forming an unlikely alliance with Statham's Deckard Shaw.","release_date":"2019-08-01"},{"vote_count":239,"id":515195,"video":false,"vote_average":6.6,"title":"Yesterday","popularity":35.858,"poster_path":"/1rjaRIAqFPQNnMtqSMLtg0VEABi.jpg","original_language":"en","original_title":"Yesterday","genre_ids":[35,10402,10749],"backdrop_path":"/t9xAqZc37OgVkojyQwT3UCanZJk.jpg","adult":false,"overview":"Jack Malik is a struggling singer-songwriter in an English seaside town whose dreams of fame are rapidly fading, despite the fierce devotion and support of his childhood best friend, Ellie. After a freak bus accident during a mysterious global blackout, Jack wakes up to discover that he's the only person on Earth who can remember The Beatles.","release_date":"2019-06-27"},{"vote_count":822,"id":438650,"video":false,"vote_average":5.4,"title":"Cold Pursuit","popularity":35.555,"poster_path":"/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg","original_language":"en","original_title":"Cold Pursuit","genre_ids":[28,18,80,53],"backdrop_path":"/aiM3XxYE2JvW1vJ4AC6cI1RjAoT.jpg","adult":false,"overview":"The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.","release_date":"2019-02-07"},{"vote_count":248,"id":502416,"video":false,"vote_average":5.6,"title":"Ma","popularity":32.565,"poster_path":"/6n7ASmQ1wY2cxTubFFGlcvPpyk7.jpg","original_language":"en","original_title":"Ma","genre_ids":[53,27],"backdrop_path":"/mBOv5YrX5QGr5CusK0PKSHuxOt9.jpg","adult":false,"overview":"Sue Ann is a loner who keeps to herself in her quiet Ohio town. One day, she is asked by Maggie, a new teenager in town, to buy some booze for her and her friends, and Sue Ann sees the chance to make some unsuspecting, if younger, friends of her own.","release_date":"2019-05-29"},{"vote_count":10,"id":519465,"video":false,"vote_average":7.6,"title":"Queen of Hearts","popularity":31.838,"poster_path":"/dfFVDIgovEfQZn53VRKLV2JQnRJ.jpg","original_language":"da","original_title":"Dronningen","genre_ids":[18],"backdrop_path":"/3xqkD4QaB9qYrOgcc5YdfMvof7Z.jpg","adult":false,"overview":"Anne, a brilliant and dedicated advocacy lawyer specialising in society\u2019s most vulnerable, children and young adults, lives what appears to be the picture-perfect life with her doctor-husband, Peter, and their twin daughters. When her estranged teenage stepson, Gustav, moves in with them, Anne\u2019s escalating desire leads her down a dangerous rabbit hole which, once exposed, unleashes a sequence of events destined to destroy her world.","release_date":"2019-03-27"},{"vote_count":14695,"id":603,"video":false,"vote_average":8.1,"title":"The Matrix","popularity":31.354,"poster_path":"/hEpWvX6Bp79eLxY1kX5ZZJcme5U.jpg","original_language":"en","original_title":"The Matrix","genre_ids":[28,878],"backdrop_path":"/icmmSD4vTTDKOq2vvdulafOGw93.jpg","adult":false,"overview":"Set in the 22nd century, The Matrix tells the story of a computer hacker who joins a group of underground insurgents fighting the vast and powerful computers who now rule the earth.","release_date":"1999-03-30"},{"vote_count":340,"id":445629,"video":false,"vote_average":6.5,"title":"Fighting with My Family","popularity":30.805,"poster_path":"/cVhe15rJLRjolunSWLBN6xQLyGU.jpg","original_language":"en","original_title":"Fighting with My Family","genre_ids":[35,18],"backdrop_path":"/mYKP6qcDUimVMAHQWLOuDHjO19S.jpg","adult":false,"overview":"Born into a tight-knit wrestling family, Paige and her brother Zak are ecstatic when they get the once-in-a-lifetime opportunity to try out for the WWE. But when only Paige earns a spot in the competitive training program, she must leave her loved ones behind and face this new cutthroat world alone. Paige's journey pushes her to dig deep and ultimately prove to the world that what makes her different is the very thing that can make her a star.","release_date":"2019-02-14"},{"vote_count":1003,"id":457799,"video":false,"vote_average":7,"title":"Extremely Wicked, Shockingly Evil and Vile","popularity":30.136,"poster_path":"/zSuJ3r5zr5T26tTxyygHhgkUAIM.jpg","original_language":"en","original_title":"Extremely Wicked, Shockingly Evil and Vile","genre_ids":[53,80,18,36],"backdrop_path":"/2l1QJBrT5nf0O6199piSGBptfZ9.jpg","adult":false,"overview":"A chronicle of the crimes of Ted Bundy, from the perspective of his longtime girlfriend, Elizabeth Kloepfer, who refused to believe the truth about him for years.","release_date":"2019-05-02"},{"vote_count":49,"id":513045,"video":false,"vote_average":6.5,"title":"Stuber","popularity":28.933,"poster_path":"/7RTaeiHvc9oPfvRMQGUra7qLOQh.jpg","original_language":"en","original_title":"Stuber","genre_ids":[28,35],"backdrop_path":"/xgfn98c2UzvFWP6MXDzytearmQ3.jpg","adult":false,"overview":"After crashing his car, a cop who's recovering from eye surgery recruits an Uber driver to help him catch a heroin dealer. The mismatched pair soon find themselves in for a wild day of stakeouts and shootouts as they encounter the city's seedy side.","release_date":"2019-07-11"}]
     * page : 1
     * total_results : 385
     * dates : {"maximum":"2019-08-15","minimum":"2019-07-23"}
     * total_pages : 20
     */

    private int page;
    private int total_results;
    private DatesBean dates;
    private int total_pages;
    private List<ResultsBean> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public DatesBean getDates() {
        return dates;
    }

    public void setDates(DatesBean dates) {
        this.dates = dates;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class DatesBean {
        /**
         * maximum : 2019-08-15
         * minimum : 2019-07-23
         */

        private String maximum;
        private String minimum;

        public String getMaximum() {
            return maximum;
        }

        public void setMaximum(String maximum) {
            this.maximum = maximum;
        }

        public String getMinimum() {
            return minimum;
        }

        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }
    }

    public static class ResultsBean extends Movie implements Parcelable {
        /**
         * vote_count : 697
         * id : 420818
         * video : false
         * vote_average : 7.2
         * title : The Lion King
         * popularity : 523.663
         * poster_path : /dzBtMocZuJbjLOXvrl4zGYigDzh.jpg
         * original_language : en
         * original_title : The Lion King
         * genre_ids : [12,16,10751,18,28]
         * backdrop_path : /1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg
         * adult : false
         * overview : Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.
         * release_date : 2019-07-12
         */

        private int vote_count;
        private int id;
        private boolean video;
        private double vote_average;
        private String title;
        private double popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private List<Integer> genre_ids;

        protected ResultsBean(Parcel in) {
            vote_count = in.readInt();
            id = in.readInt();
            video = in.readByte() != 0;
            vote_average = in.readDouble();
            title = in.readString();
            popularity = in.readDouble();
            poster_path = in.readString();
            original_language = in.readString();
            original_title = in.readString();
            backdrop_path = in.readString();
            adult = in.readByte() != 0;
            overview = in.readString();
            release_date = in.readString();
        }

        public static final Parcelable.Creator<ResultsBean> CREATOR = new Parcelable.Creator<ResultsBean>() {
            @Override
            public ResultsBean createFromParcel(Parcel in) {
                return new ResultsBean(in);
            }

            @Override
            public ResultsBean[] newArray(int size) {
                return new ResultsBean[size];
            }
        };

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public Double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(vote_count);
            dest.writeInt(id);
            dest.writeByte((byte) (video ? 1 : 0));
            dest.writeDouble(vote_average);
            dest.writeString(title);
            dest.writeDouble(popularity);
            dest.writeString(poster_path);
            dest.writeString(original_language);
            dest.writeString(original_title);
            dest.writeString(backdrop_path);
            dest.writeByte((byte) (adult ? 1 : 0));
            dest.writeString(overview);
            dest.writeString(release_date);
        }

        public ResultsBean() {

        }

        public ResultsBean(int id, String title, String released, String poster, String overview, String backdrop, double score) {
            this.id = id;
            this.title = title;
            this.release_date = released;
            this.poster_path = poster;
            this.overview = overview;
            this.backdrop_path = backdrop;
            this.vote_average = score;
        }

        public ResultsBean(Cursor cursor) {
            this.id = DatabaseContract.getColumnInt(cursor, _ID);
            this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
            this.release_date = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RELEASED);
            this.poster_path = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.POSTER);
            this.overview = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
            this.backdrop_path = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
            this.vote_average = DatabaseContract.getColumnDouble(cursor, DatabaseContract.MovieColumns.SCORE);
        }
    }
}