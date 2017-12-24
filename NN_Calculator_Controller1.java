/**
 * Controller class.
 *
 * @author Henrique Painhas
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        NaturalNumber numTop = new NaturalNumber2();
        NaturalNumber numBottom = new NaturalNumber2();
        numTop.copyFrom(model.top());
        numBottom.copyFrom(model.bottom());

        view.updateTopDisplay(numTop);
        view.updateBottomDisplay(numBottom);

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
        this.view.updateDivideAllowed(false);
        this.view.updateRootAllowed(false);

    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

        if (top.compareTo(bottom) < 0) {
            this.view.updateSubtractAllowed(false);
        } else {
            this.view.updateSubtractAllowed(true);
        }

        if (bottom.compareTo(new NaturalNumber2(0)) == 0) {
            this.view.updateDivideAllowed(false);
        } else {
            this.view.updateDivideAllowed(true);
        }

        if (bottom.compareTo(INT_LIMIT) > 0) {
            this.view.updatePowerAllowed(false);
        } else {
            this.view.updatePowerAllowed(true);
        }

        if (bottom.compareTo(TWO) < 0) {
            this.view.updateRootAllowed(false);
        } else {
            this.view.updateRootAllowed(true);
        }
    }

    @Override
    public void processEnterEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numTop.copyFrom(numBottom);

        updateViewToMatchModel(this.model, this.view);
        this.view.updateSubtractAllowed(true);

    }

    @Override
    public void processAddEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numBottom.add(numTop);
        numTop.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numTop.subtract(numBottom);
        numBottom.copyFrom(numTop);
        numTop.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numBottom.multiply(numTop);
        numTop.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        NaturalNumber remainder = new NaturalNumber2(numTop.divide(numBottom));
        numBottom.copyFrom(numTop);
        numTop.copyFrom(remainder);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numTop.power(numBottom.toInt());
        numBottom.copyFrom(numTop);
        numTop.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numTop.root(numBottom.toInt());
        numBottom.copyFrom(numTop);
        numTop.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();

        numBottom.multiplyBy10(digit);

        if (numTop.compareTo(numBottom) < 0) {
            this.view.updateSubtractAllowed(false);
        } else {
            this.view.updateSubtractAllowed(true);
        }

        if (numBottom.isZero()) {
            this.view.updateDivideAllowed(false);
        } else {
            this.view.updateDivideAllowed(true);
        }

        if (numBottom.compareTo(INT_LIMIT) > 0) {
            this.view.updatePowerAllowed(false);
        } else {
            this.view.updatePowerAllowed(true);
        }

        if (numBottom.compareTo(TWO) < 0) {
            this.view.updateRootAllowed(false);
        } else {
            this.view.updateRootAllowed(true);
        }

        updateViewToMatchModel(this.model, this.view);

    }

}
