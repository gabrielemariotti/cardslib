package it.gmariotti.cardslib.demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardGridView;

public class CardGridFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_cardgrid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_grid_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);
    }

    @Override
    public void onStart() {
        super.onStart();

        initCards();
    }

    private void initCards() {
        for (int i = 0; i < 10; i++) {
            init_simple_card(i);
        }

        CardGridView cardView = (CardGridView) getActivity().findViewById(R.id.card_grid_view);
        cardView.commitCards();
    }

    /**
     * This method builds a simple card
     */
    private void init_simple_card(int counter) {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle) + " " + counter);

        card.addCardHeader(header);

        //Set the card inner text
        card.setTitle(getString(R.string.demo_card_basetitle));

        //Set card in the cardView
        CardGridView cardView = (CardGridView) getActivity().findViewById(R.id.card_grid_view);
        cardView.addCard(card);
    }
}
