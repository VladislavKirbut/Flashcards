package flashcards.project.util;

import java.util.List;
import java.util.stream.Collectors;

public class ServletsUtils {
    public static <T> String getResponse(List<T> list) {
        return list.stream()
                .map(T::toString)
                .collect(Collectors.joining("\n"));
    }
}
