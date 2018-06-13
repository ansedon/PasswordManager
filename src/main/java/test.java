import tool.PasswordUtils.Nbvcxz;
import tool.PasswordUtils.resources.Configuration;
import tool.PasswordUtils.resources.ConfigurationBuilder;
import tool.PasswordUtils.resources.Dictionary;
import tool.PasswordUtils.resources.DictionaryBuilder;
import tool.PasswordUtils.scoring.Result;

import java.util.List;

public class test {
    public static void main(String [] args){
        List<Dictionary> dictionaryList = ConfigurationBuilder.getDefaultDictionaries();
        dictionaryList.add(new DictionaryBuilder()
                .setDictionaryName("exclude")
                .setExclusion(false)
                .addWord("r39fa8hiz0!#", 0)
//                .addWord("", 0)
//                .addWord("", 0)
                .createDictionary());

// Create our configuration object and set our custom minimum
// entropy, and custom dictionary list
        Configuration configuration = new ConfigurationBuilder()
                .setDictionaries(dictionaryList)
                .createConfiguration();

// Create our Nbvcxz object with the configuration we built
        Nbvcxz nbvcxz = new Nbvcxz(configuration);
        String password="lkjhgf";
        Result result = nbvcxz.estimate(password);

        System.out.println(result.getEntropy());
        System.out.println(result.getEntropy().intValue());
        System.out.println(result.getFeedback().getWarning());
        System.out.println(result.getFeedback().getSuggestion().toString());
    }
}
