package com.oleksandr.iakushev.oenkeeper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oleksandr.iakushev.oenkeeper.model.DataItem;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.*;

public class StorageManager {
    private static final String ALPHABET = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_";
    private static final File STORAGE_FILE = new File("storage.dat");
    private static final String RANDOM_TITLE_ITEM = "Default";
    private static final String NEW_TITLE_ITEM = "New Item";
    private static final String CRYPT_ALGORITHM = "AES";
    private static final int CRYPT_KEY_SIZE = 128 / 8; // 128 bit
    private static final String DIGEST_ALGORITHM = "SHA-256";

    private Set<String> currentList;

    StorageManager() throws IOException {
        currentList = Collections.emptySet();
        if (!STORAGE_FILE.exists()) saveStorageFileNames();
        loadStorageFileNames();
    }

    public Set<String> loadStorageFileNames() throws FileNotFoundException {
        currentList = new HashSet<>();

        try (Scanner scanner = new Scanner(STORAGE_FILE)) {
            while (scanner.hasNextLine()) {
                String fileName = scanner.nextLine();
                File file = new File(fileName);
                if (file.exists()) currentList.add(fileName);
            }
        }

        return currentList;
    }

    public void saveStorageFileNames() throws IOException {
        try (FileWriter fileWriter = new FileWriter(STORAGE_FILE)) {
            for (String line : currentList) {
                fileWriter.write(line + "\n");
            }
        }
    }

    public void addStorageFileName(String filename) {
        currentList.add(filename);
    }

    private static DataItem generateRandomDataItem() {
        DataItem item = new DataItem();
        item.setTitle(RANDOM_TITLE_ITEM);
        item.initId();
        item.setDescription(generateRandomString(100));
        item.setSensitiveInfo(generateRandomString(130));
        return item;
    }

    public static List<DataItem> generateNewStorageList() {
        List<DataItem> resultList = new ArrayList<>();
        resultList.add(generateRandomDataItem());
        return resultList;
    }

    private static String generateRandomString(int len) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = (int) (Math.random() * ALPHABET.length());
            result.append(ALPHABET.charAt(index));
        }

        return result.toString();
    }

    static List<DataItem> loadDataItems(String filename, String password) throws Exception {
        String json = FileUtils.readFileToString(new File(filename));
        return new Gson().fromJson(decryptDataJson(json, password), new TypeToken<List<DataItem>>() {
        }.getType());
    }

    public static void saveDataItems(String fileName, String password, List<DataItem> dataItems) throws Exception {
        File newStorage = new File(fileName);
        String jsonArray = new Gson().toJson(dataItems);
        FileUtils.writeStringToFile(newStorage, encryptDataJson(jsonArray, password));
    }

    public static DataItem generateNewDataItem() {
        DataItem item = new DataItem();
        item.setTitle(NEW_TITLE_ITEM);
        item.initId();
        item.setDescription(StringUtils.EMPTY);
        item.setSensitiveInfo(StringUtils.EMPTY);
        return item;
    }

    private static String encryptDataJson(String json, String password) throws Exception {
        Key key = generateKey(password);
        Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(json.getBytes()));
    }

    private static String decryptDataJson(String inData, String password) throws Exception {
        Key key = generateKey(password);
        Cipher c = Cipher.getInstance(CRYPT_ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBase64 = Base64.decodeBase64(inData);
        return new String(c.doFinal(decodedBase64));
    }

    private static Key generateKey(String password) throws Exception {
        byte[] digest = MessageDigest.getInstance(DIGEST_ALGORITHM).digest(password.getBytes());
        return new SecretKeySpec(Arrays.copyOf(digest, CRYPT_KEY_SIZE), CRYPT_ALGORITHM);
    }
}
