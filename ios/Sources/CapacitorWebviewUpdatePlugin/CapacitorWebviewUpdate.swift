import Foundation

@objc public class CapacitorWebviewUpdate: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
