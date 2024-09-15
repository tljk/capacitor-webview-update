import type { PluginListenerHandle } from '@capacitor/core';

export type sourceType = 'local' | 'remote' | 'package'; 
export type instructionType = 'arm' | 'arm64' | "x86_64"| "x86";

export interface CapacitorWebviewUpdatePlugin {
  getSystemWebView(): Promise<{ packageName: string, versionName: string }>;

  getUpgradeWebView(): Promise<{ packageName: string, versionName: string }>;

  getCurrentInstruction(): Promise<{ instruction: instructionType }>;

  compareVersion(options: { versionName: string }): Promise<{ result: number }>;

  upgradeWebView(options: { type: sourceType, source: { packageName: string, versionName: string, url: string }}): Promise<void>;

  deleteWebView(options: { path: string }): Promise<void>;

  applyWebView(): Promise<void>;

  addListener(
    eventName: 'upgradeProcess',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  addListener(
    eventName: 'upgradeComplete',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  addListener(
    eventName: 'upgradeError',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  removeAllListeners(): Promise<void>;
}
