package oncall;

import oncall.controller.OnCallController;

public class Application {
    public static void main(String[] args) {
        OnCallController onCallController = new OnCallController();
        onCallController.process();
    }
}
