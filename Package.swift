// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorWebviewUpdate",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorWebviewUpdate",
            targets: ["CapacitorWebviewUpdatePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "CapacitorWebviewUpdatePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapacitorWebviewUpdatePlugin"),
        .testTarget(
            name: "CapacitorWebviewUpdatePluginTests",
            dependencies: ["CapacitorWebviewUpdatePlugin"],
            path: "ios/Tests/CapacitorWebviewUpdatePluginTests")
    ]
)