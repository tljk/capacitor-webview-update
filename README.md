# capacitor-webview-update

This is a capacitor wrapper for [JonaNorman/WebViewUpgrade](https://github.com/JonaNorman/WebViewUpgrade).

## Install

```bash
npm install capacitor-webview-update
npx cap sync
```

## API

<docgen-index>

* [`getSystemWebView()`](#getsystemwebview)
* [`getUpgradeWebView()`](#getupgradewebview)
* [`getCurrentInstruction()`](#getcurrentinstruction)
* [`compareVersion(...)`](#compareversion)
* [`upgradeWebView(...)`](#upgradewebview)
* [`deleteWebView(...)`](#deletewebview)
* [`applyWebView()`](#applywebview)
* [`addListener('upgradeProcess', ...)`](#addlistenerupgradeprocess-)
* [`addListener('upgradeComplete', ...)`](#addlistenerupgradecomplete-)
* [`addListener('upgradeError', ...)`](#addlistenerupgradeerror-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getSystemWebView()

```typescript
getSystemWebView() => Promise<{ packageName: string; versionName: string; }>
```

**Returns:** <code>Promise&lt;{ packageName: string; versionName: string; }&gt;</code>

--------------------


### getUpgradeWebView()

```typescript
getUpgradeWebView() => Promise<{ packageName: string; versionName: string; }>
```

**Returns:** <code>Promise&lt;{ packageName: string; versionName: string; }&gt;</code>

--------------------


### getCurrentInstruction()

```typescript
getCurrentInstruction() => Promise<{ instruction: instructionType; }>
```

**Returns:** <code>Promise&lt;{ instruction: <a href="#instructiontype">instructionType</a>; }&gt;</code>

--------------------


### compareVersion(...)

```typescript
compareVersion(options: { versionName: string; }) => Promise<{ result: number; }>
```

| Param         | Type                                  |
| ------------- | ------------------------------------- |
| **`options`** | <code>{ versionName: string; }</code> |

**Returns:** <code>Promise&lt;{ result: number; }&gt;</code>

--------------------


### upgradeWebView(...)

```typescript
upgradeWebView(options: { type: sourceType; source: { packageName: string; versionName: string; url: string; }; }) => Promise<void>
```

| Param         | Type                                                                                                                             |
| ------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ type: <a href="#sourcetype">sourceType</a>; source: { packageName: string; versionName: string; url: string; }; }</code> |

--------------------


### deleteWebView(...)

```typescript
deleteWebView(options: { path: string; }) => Promise<void>
```

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ path: string; }</code> |

--------------------


### applyWebView()

```typescript
applyWebView() => Promise<void>
```

--------------------


### addListener('upgradeProcess', ...)

```typescript
addListener(eventName: 'upgradeProcess', listenerFunc: () => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                          |
| ------------------ | ----------------------------- |
| **`eventName`**    | <code>'upgradeProcess'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>    |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('upgradeComplete', ...)

```typescript
addListener(eventName: 'upgradeComplete', listenerFunc: () => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>'upgradeComplete'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('upgradeError', ...)

```typescript
addListener(eventName: 'upgradeError', listenerFunc: () => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                        |
| ------------------ | --------------------------- |
| **`eventName`**    | <code>'upgradeError'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>  |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### instructionType

<code>'arm' | 'arm64' | "x86_64" | "x86"</code>


#### sourceType

<code>'local' | 'remote' | 'package'</code>

</docgen-api>
