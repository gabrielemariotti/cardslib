package it.gmariotti.cardslib.library;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class Constants {


    public static class IntentManager{

        /**
         * Intent Action for downloaded images
         */
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED = "it.gmariotti.cardslib.library.intent.action.IMAGE_DOWNLOADED";

        /**
         * Extra for download result
         */
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT  = "ExtraResult";

        /**
         * Extra for {@link it.gmariotti.cardslib.library.internal.Card} id
         */
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID  = "ExtraCardId";

    }

}
