/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

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
         * Extra for download process
         */
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING  = "ExtraErrorLoading";

        /**
         * Extra for {@link it.gmariotti.cardslib.library.internal.Card} id
         */
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID  = "ExtraCardId";




    }

}
