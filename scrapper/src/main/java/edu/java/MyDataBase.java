package edu.java;

import edu.java.DTO.AllLinksDTO;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyDataBase {
    private static final Map<Long, List<URI>> DATABASE = new HashMap<>();
    private static final String REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private MyDataBase() {
    }

    public static void register(Long id) {
        DATABASE.put(id, new ArrayList<>());
    }

    public static boolean add(Long chatId, URI link) {
//        if (PATTERN.matcher(link).matches()) {
        DATABASE.merge(chatId, new ArrayList<>(List.of(link)), (existingList, newList) -> {
            existingList.addAll(newList);
            return existingList;
        });
        return true;
//        } else {
//            return false;
//        }
    }

    public static boolean remove(Long chatId, URI link) {
        if (
//            PATTERN.matcher(link).matches() &&
            DATABASE.containsKey(chatId) && DATABASE.get(chatId).contains(link)) {
            DATABASE.merge(chatId, List.of(), (existingList, newList) -> {
                existingList.remove(link);
                return existingList;
            });
            return true;
        } else {
            return false;
        }
    }

    public static AllLinksDTO getLinks(Long chatId) {
        log.warn("getLinks " + DATABASE.get(chatId));
        return new AllLinksDTO(chatId, DATABASE.get(chatId));
    }
}
