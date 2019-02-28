import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InputImpl implements Input {
    private List<VertPictureImpl> verts = new ArrayList<>();
    private List<SlideImpl> slides = new ArrayList<>();

    public InputImpl(String path) {
        try {
            parse(path);
            verts.sort(new Comparator<VertPictureImpl>() {
                @Override
                public int compare(VertPictureImpl o1, VertPictureImpl o2) {
                    return o1.getTags().size() - o2.getTags().size();
                }
            });
            slides.sort(new Comparator<SlideImpl>() {
                @Override
                public int compare(SlideImpl o1, SlideImpl o2) {
                    return o1.getTags().size() - o2.getTags().size();
                }
            });
        }catch (IOException e){
            System.out.println("Error while parsing!");
        }
    }

    public void parse(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        line = br.readLine();
        int count = Integer.parseInt(line);
        for (int x = 0; x < count; x++){
            line = br.readLine();
            String[] parted = line.split(" ");
            if (parted[0].equals("H")){
                ArrayList<String> tags = new ArrayList<>();
                for (int y = 2; y < parted.length; y++){
                    tags.add(parted[y]);
                }
                java.util.Collections.sort(tags);
                SlideImpl toAdd = new SlideImpl(tags, new int[]{x});
                this.slides.add(toAdd);
            }else {
                VertPictureImpl pic = new VertPictureImpl(x);
                for (int y = 2; y < parted.length; y++) {
                    pic.getTags().add(parted[y]);
                }
                java.util.Collections.sort(pic.getTags());
                this.verts.add(pic);
            }
        }
        br.close();
    }

    @Override
    public List<SlideImpl> getSlide() {
        return this.slides;
    }

    @Override
    public List<VertPictureImpl> getPictures() {
        return this.verts;
    }
}