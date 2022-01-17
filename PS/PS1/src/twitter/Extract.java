/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    private final static char AT_CHAR = '@';

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        assert tweets.size() != 0;

        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();

        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().compareTo(start) < 0) {
                start = tweet.getTimestamp();
            }

            if (tweet.getTimestamp().compareTo(end) > 0) {
                end = tweet.getTimestamp();
            }
        }

        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        for (Tweet tweet : tweets) {
            char[] charArray = tweet.getText().toCharArray();
            for (int i = 0; i < charArray.length - 1; i++) {  // the AT sign at the end of Text should not be considered
                if (charArray[i] == AT_CHAR) {
                    if (i > 0) {
                        if (!isAllowedTweetUserChar(charArray[i - 1])) {
                            String user = getMentionedUser(charArray, i);
                            if (user.length() > 0) {
                                user = user.toUpperCase(Locale.ROOT);
                                mentionedUsers.add(user);
                                i += user.length();
                            }
                        }
                    } else {
                        String user = getMentionedUser(charArray, i);
                        if (user.length() > 0) {
                            user = user.toUpperCase(Locale.ROOT);
                            mentionedUsers.add(user);
                            i += user.length();
                        }
                    }
                }
            }
        }

        return mentionedUsers;
    }

    public static boolean isAllowedTweetUserChar(char theChar) {
        return (theChar >= 'a' && theChar <= 'z' || theChar >= 'A' && theChar <= 'Z' || theChar == '_' || theChar == '-');
    }

    private static String getMentionedUser(char[] charArray, int start) {
        StringBuilder sb = new StringBuilder();
        for (int i = start + 1; i < charArray.length; i++) {
            if (isAllowedTweetUserChar(charArray[i])) {
                sb.append(charArray[i]);
            } else {
                break;
            }
        }
        return sb.toString();
    }
}
