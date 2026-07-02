import java.util.concurrent.ConcurrentHashMap;

// a simulated phone in the mesh
// in the real system this state would be on a physical android device , 
// with packets exchanged via BLE GATT characterstics
public class VirtualDevice {
  private final String deviceId;
  private final boolean hasInternet;
  private final Map<String, MeshPacket> heldPackets = new ConcurrentHashMap<>();

  public VirtualDevice(String deviceId, boolean hasInternet) {
    this.deviceId = deviceId;
    this.hasInternet = hasInternet;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public boolean hasInternet() {
    return hasInternet;
  }

  public void hold(MeshPacket packet) {
    heldPackets.putIfAbsent(packet.getPacketId(), packet);
  }

  public Collection<MeshPacket> getHeldPackets() {
    return heldPackets.values();
  }

  public boolean holds(String packetId) {
    return heldPackets.containsKey(packetId);
  }

  public int packetCount() {
    return heldPackets.containsKey(packetId);
  }

  public void clear() {
    heldPackets.clear();
  }
}
