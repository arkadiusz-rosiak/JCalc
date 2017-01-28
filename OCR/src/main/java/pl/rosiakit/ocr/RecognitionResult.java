package pl.rosiakit.ocr;

import java.util.ArrayList;
import java.util.List;

public class RecognitionResult {

    private final List<String> lines = new ArrayList<>();

    void addLine(List<Character> characters) {
        StringBuilder sb = new StringBuilder();
        characters.forEach(sb::append);
        lines.add(sb.toString());
    }

    public List<String> getLines() {
        return lines;
    }

    public String getText(){
        StringBuilder sb = new StringBuilder();

        for(String line : lines){
            sb.append(line).append("\n");
        }

        return sb.toString();
    }


}
