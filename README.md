# 🔐 Ghaida System - One Time Pad Encryptor

A lightweight Java-based GUI tool for secure file and folder encryption using the **One-Time Pad (OTP)** algorithm.

This system ensures maximum security by encrypting each file with a truly random key that is used **only once**, making decryption mathematically unbreakable when used correctly.

---

## 📦 Features

- 🔐 **Encrypt File**: Encrypts a selected file using a one-time pad. The original file is deleted after encryption.
- 🔓 **Decrypt File**: Decrypts an encrypted `.enc` file using its corresponding `.key` file.
- 🗂️ **Encrypt Folder**: Encrypts all valid files in a selected folder (excluding `.enc` and `.key` files), and deletes originals.
- 🧊 Simple and friendly **Graphical User Interface (GUI)** using Swing.
- 🧠 Automatic key generation per file, stored separately as `.key`.

---

## 🚀 Getting Started

### Prerequisites

- Java 8 or later
- IDE like NetBeans / IntelliJ / VS Code OR command line with `javac` and `java`

### File Structure

