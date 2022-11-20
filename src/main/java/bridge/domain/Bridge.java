package bridge.domain;

import bridge.exception.domain.AttemptsCountException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bridge {
    private final List<BridgeShape> bridgeShapes;

    public Bridge() {
        this.bridgeShapes = new ArrayList<>();
    }

    public Bridge(List<BridgeShape> bridgeShapeValues) {
        this.bridgeShapes = new ArrayList<>(bridgeShapeValues);
    }

    public static Bridge createByBridgeShapeValue(List<String> bridgeShapeValues) {
        return new Bridge(bridgeShapeValues.stream()
                .map(BridgeShape::of)
                .collect(Collectors.toList()));
    }

    public void connect(BridgeShape bridgeShape) {
        bridgeShapes.add(bridgeShape);
    }

    public BridgeGameResult compare(Bridge compareBridge) {
        validateAttemptsCount(compareBridge);

        List<Boolean> booleans = IntStream.range(0, compareBridge.bridgeShapes.size())
                .mapToObj(index -> compareBridge.bridgeShapes.get(index) == this.bridgeShapes.get(index))
                .collect(Collectors.toList());
        return new BridgeGameResult(this, booleans);
    }

    private void validateAttemptsCount(Bridge compareBridge) {
        if (compareBridge.bridgeShapes.size() > this.bridgeShapes.size()) {
            throw new AttemptsCountException();
        }
    }

    public boolean sizeEqual(Bridge compareBridge) {
        return this.bridgeShapes.size() == compareBridge.bridgeShapes.size();
    }

    public List<BridgeShape> bridgeShapes() {
        return this.bridgeShapes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bridge bridge = (Bridge) o;
        return Objects.equals(bridgeShapes, bridge.bridgeShapes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bridgeShapes);
    }
}
