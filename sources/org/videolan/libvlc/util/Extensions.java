package org.videolan.libvlc.util;

import com.luck.picture.lib.config.PictureMimeType;
import java.util.Arrays;
import java.util.HashSet;

/* JADX INFO: loaded from: classes5.dex */
public class Extensions {
    public static final HashSet<String> AUDIO;
    public static final HashSet<String> PLAYLIST;
    public static final HashSet<String> SUBTITLES;
    public static final HashSet<String> VIDEO;

    static {
        HashSet<String> hashSet = new HashSet<>();
        VIDEO = hashSet;
        HashSet<String> hashSet2 = new HashSet<>();
        AUDIO = hashSet2;
        HashSet<String> hashSet3 = new HashSet<>();
        SUBTITLES = hashSet3;
        HashSet<String> hashSet4 = new HashSet<>();
        PLAYLIST = hashSet4;
        String[] strArr = {".3g2", ".3gp", ".3gp2", ".3gpp", ".amv", ".asf", PictureMimeType.AVI, ".bik", ".divx", ".drc", ".dv", ".f4v", ".flv", ".gvi", ".gxf", ".h264", ".ismv", ".iso", ".m1v", ".m2v", ".m2t", ".m2ts", ".m4v", ".mkv", ".mov", ".mp2", ".mp2v", ".mp4", ".mp4v", ".mpe", ".mpeg", ".mpeg1", ".mpeg2", ".mpeg4", ".mpg", ".mpv2", ".mts", ".mtv", ".mxf", ".mxg", ".nsv", ".nut", ".nuv", ".ogm", ".ogv", ".ogx", ".ps", ".rec", ".rm", ".rmvb", ".rpl", ".thp", ".tod", ".ts", ".tts", ".vob", ".vro", ".webm", ".wm", ".wmv", ".wtv", ".xesc"};
        String[] strArr2 = {".3ga", ".669", ".a52", ".aac", ".ac3", ".adt", ".adts", ".aif", ".aifc", ".aiff", ".alac", PictureMimeType.AMR, ".aob", ".ape", ".au", ".awb", ".caf", ".dts", ".flac", ".it", ".m4a", ".m4b", ".m4p", ".mid", ".mka", ".mlp", ".mod", ".mpa", ".mp1", ".mp2", PictureMimeType.MP3, ".mpc", ".mpga", ".oga", ".ogg", ".oma", ".opus", ".qcp", ".ra", ".ram", ".rmi", ".s3m", ".snd", ".spx", ".tta", ".voc", ".vqf", ".w64", PictureMimeType.WAV, ".wma", ".wv", ".xa", ".xm"};
        hashSet.addAll(Arrays.asList(strArr));
        hashSet2.addAll(Arrays.asList(strArr2));
        hashSet3.addAll(Arrays.asList(".idx", ".sub", ".srt", ".ssa", ".ass", ".smi", ".utf", ".utf8", ".utf-8", ".rt", ".aqt", ".txt", ".usf", ".jss", ".cdg", ".psb", ".mpsub", ".mpl2", ".pjs", ".dks", ".stl", ".vtt", ".ttml", ".mks"));
        hashSet4.addAll(Arrays.asList(".m3u", ".asx", ".b4s", ".pls", ".xspf", ".wpl"));
    }
}
